package org.puzre.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Cookie {

    private String name;
    private String value;
    private String path;
    private String domain;
    private long maxAge;
    private boolean secure;
    private boolean httpOnly;

}
