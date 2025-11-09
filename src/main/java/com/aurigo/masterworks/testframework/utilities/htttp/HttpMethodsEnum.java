package com.aurigo.masterworks.testframework.utilities.htttp;

public enum HttpMethodsEnum  {

    eGet("GET", 1),
    ePost("POST", 2),
    ePut("PUT", 3),
    eDelete("DELETE", 4),
    ePatch("PATCH", 5);

    private String _desc;
    private long _id;

    HttpMethodsEnum(String desc, long id) {
        _desc = desc;
        _id = id;
    }

    /**
     * Get HTTP Method ENUM using ID.
     *
     * @param id - Http Verb id.
     * @return HTTP Method
     */
    public static HttpMethodsEnum getEnumById(long id) {
        HttpMethodsEnum[] types = HttpMethodsEnum.values();

        for (HttpMethodsEnum type : types) {
            if (type._id == id) {
                return type;
            }
        }
        throw new EnumConstantNotPresentException(HttpMethodsEnum.class, Long.toString(id));
    }

    /**
     * Get Http verb using enum description.
     *
     * @param desc enum description.
     * @return HTTP Method
     */
    public static HttpMethodsEnum getEnumByDesc(String desc) {
        HttpMethodsEnum[] types = HttpMethodsEnum.values();

        for (HttpMethodsEnum type : types) {
            if (type._desc.equals(desc)) {
                return type;
            }
        }

        throw new EnumConstantNotPresentException(HttpMethodsEnum.class, desc);
    }

    /**
     * Get Http Verb Id.
     *
     * @param description - enum description.
     * @return Http Verb id.
     */
    public long getId(String description) {
        return getEnumByDesc(description)._id;
    }

    /**
     * Get Enum
     *
     * @return -   Enum
     */
    public final HttpMethodsEnum getEnum() {
        return this;
    }

    /**
     * Get enum member description.
     *
     * @return -   enum member description.
     */
    public final String getDescription() {
        return _desc;
    }

    /**
     * Get Enum member Id (value).
     *
     * @return -   Enum member Id (value).
     */
    public final long getId() {
        return _id;
    }

}
