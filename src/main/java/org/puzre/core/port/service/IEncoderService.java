package org.puzre.core.port.service;

public interface IEncoderService {

    String hash(String string);
    boolean matches(String rawString, String hashedString);

}
