package de.github.sebyplays.hashhandler.api;

import de.github.sebyplays.logmanager.api.LogManager;
import de.github.sebyplays.logmanager.api.LogType;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashHandler {
    private static HashType hashType;
    private static String hashTypeString;
    public HashHandler(HashType hashType){
        this.hashTypeString = null;
        this.hashType = hashType;
    }

    public HashHandler(String hashTypeFromString){
        this.hashType = null;
        this.hashTypeString = hashTypeFromString;
    }

    public String hash(String inputHash, boolean log) throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigest;
        if(hashTypeString != null){
            messageDigest = MessageDigest.getInstance(HashHandler.getTypeString());
        } else {
            messageDigest = MessageDigest.getInstance(HashHandler.getType().toString().replace("_", "-"));
        }
        messageDigest.update(inputHash.getBytes());

        byte[] digest = messageDigest.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (byte byt : digest){
            stringBuffer.append(String.format("%02x", byt & 0xff));
        }
        if(log){
            if(hashTypeString != null){
                LogManager.getLogManager("hashlog").log(LogType.NORMAL, inputHash + " -> " + HashHandler.getTypeString() + " -> " + stringBuffer.toString(),true, true);
                return stringBuffer.toString();
            }
            LogManager.getLogManager("hashlog").log(LogType.NORMAL, inputHash + " -> " + HashHandler.getType().toString().replace("_", "-") + " -> " + stringBuffer.toString(),true, true);
        }
        return stringBuffer.toString();
    }

    private static HashType getType() {
        return hashType;
    }

    private static String getTypeString() {
        return hashTypeString;
    }

}

