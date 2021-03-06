package redempt.redlex.token;

import redempt.redlex.data.LexContext;
import redempt.redlex.data.ParentToken;
import redempt.redlex.data.Token;
import redempt.redlex.data.TokenType;

import java.util.*;

public class ChoiceToken extends TokenType implements ParentToken {

	private Map<Character, List<TokenType>> firstChars;
	private TokenType[] children;
	private int minLength = -1;
	
	public ChoiceToken(String name, TokenType... children) {
		super(name);
		this.children = children;
	}

	private void initMap() {
		if (firstChars != null) {
			return;
		}
		firstChars = new HashMap<>();
		for (TokenType child : children) {
			for (Character c : child.getFirstCharacters()) {
				firstChars.computeIfAbsent(c, k -> new ArrayList<>()).add(child);
			}
		}
	}

	private void initLength() {
		if (minLength != -1) {
			return;
		}
		minLength = 0;
		for (TokenType child : children) {
			minLength = Math.min(child.minLength(), minLength);
		}
	}
	
	@Override
	protected Token findForward(String str, int pos, LexContext ctx) {
		initMap();
		if (pos < str.length()) {
			char c = str.charAt(pos);
			List<TokenType> types = firstChars.get(c);
			Token token = tryParse(types, str, pos, ctx);
			if (token != null) {
				return token;
			}
		}
		Token token = tryParse(firstChars.get(null), str, pos, ctx);
		return token;
	}

	private Token tryParse(List<TokenType> children, String str, int pos, LexContext ctx) {
		if (children == null) {
			return null;
		}
		for (TokenType child : children) {
			Token inst = child.tryTokenize(str, pos, ctx);
			if (inst != null) {
				return new Token(this, inst.getBaseString(), inst.getStart(), inst.getEnd(), new Token[] {inst});
			}
		}
		return null;
	}
	
	@Override
	public int minLength() {
		initLength();
		return minLength;
	}

	@Override
	protected List<Character> calcFirstCharacters() {
		Set<Character> chars = new HashSet<>();
		for (TokenType child : children) {
			chars.addAll(child.getFirstCharacters());
		}
		return new ArrayList<>(chars);
	}

	@Override
	public TokenType[] getChildren() {
		return children;
	}
	
	@Override
	public void setChildren(TokenType[] children) {
		this.children = children;
		firstChars = null;
	}
	
}
