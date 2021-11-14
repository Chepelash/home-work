package com.sbrf.reboot.repository;

import com.sbrf.reboot.dto.Account;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
    private final String CLIENT_ID_TAG = "clientId";
    private final String NUMBER_TAG = "number";
    private final String PATTERN = "\"" + CLIENT_ID_TAG + "\":\\s?(?<" +
                                   CLIENT_ID_TAG + ">\\d+),\\s+\"" + NUMBER_TAG +
                                   "\":\\s?\"(?<" + NUMBER_TAG + ">\\S+)\"";
    private String fpath;

    /**
     *
     * @param fpath - path to file
     * @return null - if IOException happened;
     *         String with file content.
     * @throws FileNotFoundException - if the file does not exist,
     *         is a directory rather than a regular file, or for some other reason cannot be opened for reading
     */
    private String readFileToString(String fpath) throws FileNotFoundException{
        // FileInputStream(file) throws FileNotFoundException if the file does not exist,
        // is a directory rather than a regular file, or for some other reason cannot be opened for reading
        StringBuilder sb = new StringBuilder();
        try(InputStream inputStream = new FileInputStream(fpath)){
            int content;
            while((content = inputStream.read()) != -1)
                sb.append((char) content);
        } catch (IOException e){
            if(e.getClass().equals(FileNotFoundException.class))
                throw new FileNotFoundException();
            return null;
        }
        return sb.toString();
    }

    /**
     *
     * @param fpath - path to Accounts file
     * @return null - if reading error happened;
     *         Map<Long clientIds, Set<Account> accounts> - accounts contains Account entries with number
     * @throws FileNotFoundException - if the file does not exist,
     *         is a directory rather than a regular file, or for some other reason cannot be opened for reading
     */
    private Map<Long, Set<Account>> parseFile(@NotNull String fpath) throws FileNotFoundException {

        Map<Long, Set<Account>> map = new HashMap();  // dictionary <key = clientId, value = Set<Account>>

        String fileContent = readFileToString(fpath);
        if(fileContent == null)
            return map;  // empty hash map
        // parse string and fill map
        String[] entries = fileContent.split("},");
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher;
        for(String entry: entries){
            matcher = pattern.matcher(entry);
            if(matcher.find()){
                Long clientId = Long.parseLong(matcher.group(CLIENT_ID_TAG));
                String number = matcher.group(NUMBER_TAG);
                if(map.containsKey(clientId)){
                    // clientId exists
                    // get old value
                    Set<Account> accounts = map.get(clientId);
                    // add new number
                    accounts.add(new Account(number));
                    // replace
                    map.replace(clientId, accounts);
                } else {
                    // add new cliendId
                    Set<Account> accounts = new HashSet();
                    accounts.add(new Account(number));
                    map.put(clientId, accounts);
                }
            }
        }

        return map;
    }

    private void writeDocument(String fpath, String stringToWrite){
        Path path = Paths.get(fpath);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(stringToWrite);
        } catch (IOException e){
            // TODO
            e.printStackTrace();
        }
    }

    private String formDocument(Map<Long, Set<Account>> accountMap){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Set<Long> keySet = accountMap.keySet();
        for (Long key: keySet) {
            Set<Account> accounts = accountMap.get(key);
            for(Account account: accounts){
                sb.append("\n  {\n");
                sb.append("    \"" + CLIENT_ID_TAG + "\": ");
                sb.append(key.toString()).append(",\n");
                sb.append("    \"" + NUMBER_TAG + "\": ");
                sb.append("\"").append(account.getNumber()).append("\"\n");
                sb.append("  },");
            }
        }
        sb.append("\n]");
        return sb.toString();
    }

    @Override
    public Set<Account> getAllAccountsByClientId(long id) throws FileNotFoundException {
        // parse file
        Map<Long, Set<Account>> accountMap = parseFile(this.fpath);

        // get set<account> and return it
        return accountMap.get(id);  // if key not found returns null
    }

    /**
     *
     * @param id - client's id
     * @param oldNumber - account number to change
     * @param newNumber - new account number
     * @throws NoSuchFieldException - if id or oldNumber are not found
     * @throws FileNotFoundException - if the file does not exist,
     *          is a directory rather than a regular file, or for some other reason cannot be opened for reading
     */
    public void updateClientNumber(long id, String oldNumber, String newNumber) throws NoSuchFieldException,
                                                                                       FileNotFoundException {
        // parse file
        Map<Long, Set<Account>> accountMap = parseFile(this.fpath);
        // find account and change it
        if(!accountMap.containsKey(id))
            throw new NoSuchFieldException("Client id not found");
        Set<Account> accounts = accountMap.get(id);
        boolean foundAccountNumber = false;
        for(Account account: accounts){
            if(account.getNumber().equals(oldNumber)){
                account.setNumber(newNumber);
                foundAccountNumber = true;
                break;
            }
        }
        if(!foundAccountNumber)
            throw new NoSuchFieldException("Account number not found");
        // change map
        accountMap.replace(id, accounts);
        // write file
        String stringToWrite = formDocument(accountMap);
        writeDocument(this.fpath, stringToWrite);
    }

}
