package org.puzre.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class Cookie {

    private String name;
    private String value;
    private String path;
    private String domain;
    private int maxAge;
    private boolean secure;
    private boolean httpOnly;

}
