package com.aurigo.masterworks.testframework.webUI.constants.enums;

public enum RegexStrings {

    backwardSlash("\\\\"),
    trailingZero("0*$"),
    trailingDot("\\.$"),
    notDigit("[^0-9]"),
    forwardSlash("/"),
    space(" "),
    comma(","),
    hyphen("-"),
    digitsOneOrMore("[0-9]+"),
    notAlphabetOrDotOneOrMore("[^\\d.]+"),
    notASpace("\\s"),
    notDigitOrDot("[^0-9\\.]"),
    colon(":"),
    carriageReturnCharacter("\\\\r"),
    lineFeedCharacter("\\\\n"),
    tab("\\\\t"),
    equals("="),
    spaceOneOrMore("\\s+"),
    percentSign("%");

    private String value;

    RegexStrings(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

