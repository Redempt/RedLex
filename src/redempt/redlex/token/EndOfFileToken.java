package redempt.redlex.token;

import redempt.redlex.data.LexContext;
import redempt.redlex.data.Token;
import redempt.redlex.data.TokenType;

import java.util.Collections;
import java.util.List;

public class EndOfFileToken extends TokenType {
	
	public EndOfFileToken(String name) {
		super(name);
	}
	
	@Override
	public boolean characterMatches(String input, int pos, int offset) {
		return false;
	}
	
	@Override
	public Token findForward(String str, int pos, LexContext ctx) {
		return pos == str.length() ? new Token(this, "", 0, 0) : null;
	}
	
	@Override
	public boolean lengthMatches(int length) {
		return false;
	}
	
	@Override
	public int minLength() {
		return 0;
	}
	
	@Override
	public int maxLength() {
		return 0;
	}

	@Override
	public List<Character> calcFirstCharacters() {
		return Collections.singletonList(null);
	}

}
