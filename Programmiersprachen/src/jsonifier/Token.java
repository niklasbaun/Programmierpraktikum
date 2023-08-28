package jsonifier;

public class Token {
	
	TokenType type;
    Object content;
    
    public Token(Object content, TokenType type) {
    	this.type = type;
        this.content = content;
    }

    public TokenType getTokenType() {
    	return this.type;
    }
    
    public Object getContent() {
        return this.content;
    }

    public String toString() {
    	String out = "(" + content + " , " + type + ")";
    	return out;
    }
    
    ///////
    public Boolean assertType(TokenType type) {
    	return (this.type.equals(type));
    }
}