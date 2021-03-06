package redempt.redlex.token;

import redempt.redlex.data.LexContext;
import redempt.redlex.data.ParentToken;
import redempt.redlex.data.Token;
import redempt.redlex.data.TokenType;

import java.util.Collections;
import java.util.List;

public class NotToken extends TokenType implements ParentToken {
	
	private TokenType token;
	
	public NotToken(String name, TokenType token) {
		super(name);
		this.token = token;
	}
	
	@Override
	protected Token findForward(String str, int pos, LexContext ctx) {
		Token inst = token.tryTokenize(str, pos, ctx);
		if (inst == null) {
			return new Token(this, "", 0, 0);
		}
		return null;
	}
	
	@Override
	public int minLength() {
		return 0;
	}

	@Override
	protected List<Character> calcFirstCharacters() {
		return Collections.singletonList(null);
	}

	@Override
	public String getMessage() {
		return "Unexpected";
	}
	
	@Override
	public TokenType[] getChildren() {
		return new TokenType[] {token};
	}
	
	@Override
	public void setChildren(TokenType[] children) {
		this.token = children[0];
	}
	
}
