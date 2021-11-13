package com.sbrf.reboot.repository;

import com.sbrf.reboot.dto.Account;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;

import java.io.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {
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
        InputStream inputStream = new FileInputStream(fpath);
        try{
            int content;
            while((content = inputStream.read()) != -1)
                sb.append((char) content);
        } catch (IOException e){
            // read error
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
            return null;
        // parse string and fill map
        String[] entries = fileContent.split("},");
        Pattern pattern = Pattern.compile("\"clientId\":\\s?(?<clientId>\\d+),\\s+\"number\":\\s?\"(?<number>\\S+)\"");
        Matcher matcher;
        for(String entry: entries){
            matcher = pattern.matcher(entry);
            if(matcher.find()){
                Long clientId = Long.parseLong(matcher.group("clientId"));
                String number = matcher.group("number");
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

    @Override
    public Set<Account> getAllAccountsByClientId(long id) throws FileNotFoundException {
        // parse file
        Map<Long, Set<Account>> accountMap = parseFile(this.fpath);

        // get set<account> and return it
        if(accountMap == null)
            return null;
        return accountMap.get(id);
    }
}
