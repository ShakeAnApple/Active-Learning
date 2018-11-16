package automaton.transitionFormula.boolExprHierarchy;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BooleanExprParser {

    enum OpType {
        And,
        Or
    }

    enum TokenType {
        Name,
        Neg,
        Index,
        OpenBracket,
        CloseBracket,
        Whitespace,
        Comma
    }

    //private final Map<TokenType, String> _patternTokenByKind;
    //private final String _tokenizerPattern,
    private final Pattern _tokenizer;

    public BooleanExprParser() {
        Map<TokenType, String> patternTokenByType = new HashMap<>() {{
            put(TokenType.Name, "[a-zA-Z_][a-zA-Z_0-9]*");
            put(TokenType.Neg, "[~]");
            put(TokenType.Index, "\\[[0-9]+\\]");
            put(TokenType.OpenBracket, "\\(");
            put(TokenType.CloseBracket, "\\)");
            put(TokenType.Whitespace, "[\\s\\t\\r\\n]+");
            put(TokenType.Comma, "\\,");
        }};
        String anyTokenPattern = String.join("|",
                patternTokenByType.entrySet().stream()
                        .map(kv -> "(?<" + kv.getKey() + ">" + kv.getValue() + ")").collect(Collectors.toList()));
        String tokenizerPattern = "^(" + anyTokenPattern + ")*$";

        _tokenizer = Pattern.compile(tokenizerPattern);
    }

    private class Token {
        private final TokenType _type;
        private final int _pos;
        private final String _text;

        public Token(TokenType type, int pos, String text) {
            _type = type;
            _pos = pos;
            _text = text;
        }

        public TokenType getType() {
            return _type;
        }

        public int getPos() {
            return _pos;
        }

        public String getText() {
            return _text;
        }

        @Override
        public String toString() {
            return _type + " " + _pos;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj != null && obj instanceof Token) {
                Token other = (Token) obj;
                return other._pos == _pos
                        && other._type.equals(_type);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
    }

    public class IntegerRef {
        private Integer integer;
        public IntegerRef( Integer e ){
            integer = e;
        }
        public int increment(){
            return integer++;
        }
        public Integer get() { return integer; }

        public String toString() {
            return integer.toString();
        }
    }

    public BoolExpr tryParse(String text) {
        String s = text.substring(0, 1);
        Set<Token> tokens = new HashSet<>();

        Matcher m;
        for (int i = 2; i < text.length(); i++){
            m = _tokenizer.matcher(s);
            if (m.find()){
                TokenType[] tokenTypes = TokenType.values();
                for (TokenType ttype: tokenTypes){
                    String match = m.group(ttype.name());
                    if (match != null) {
                        Token token = new Token(ttype, s.lastIndexOf(match), match);
                        if (tokens.contains(token)){
                            tokens.remove(token);
                        }
                        tokens.add(token);
                    }
                }
            }
            s = text.substring(0,i);
        }

        List<Token> found = tokens.stream().sorted(Comparator.comparing(Token::getPos))
                .filter(t -> t.getType() != TokenType.Whitespace).collect(Collectors.toList());

        BoolExpr expr = this.parse(found);

        return expr;
    }

    BoolExpr parse(List<Token> tokens)
    {
        IntegerRef n = new IntegerRef(0);

        if (tokens.get(n.increment()).getType() != TokenType.OpenBracket)
            return null;

        BoolExpr expr = this.parseExpr(tokens, n);
        if (expr == null)
            return null;

//        if (n.get() < tokens.size()) {
//            if (tokens.get(n.increment()).getType() != TokenType.Comma)
//                return null;
//
//            if (tokens.get(n.increment()).getType() != TokenType.CloseBracket)
//                return null;
//
//            if (n.get() < tokens.size())
//                return null;
//        }

        return expr;
    }

    BoolExpr parseExpr(List<Token> tokens, IntegerRef n)
    {
        BoolExpr expr;
        Token t = tokens.get(n.get());

        if (t.getType() == TokenType.Name)
        {
            if (tokens.get(n.get() + 1).getType() == TokenType.OpenBracket)
            {
                expr = this.parseOperation(tokens, n);
            }
            else
            {
                expr = this.parseVar(tokens, n);
            }
        }
        else if (t.getType() == TokenType.Neg)
        {
            expr = this.parseVar(tokens, n);
        }
        else
        {
            expr = null;
        }

        return expr;
    }

    BoolExpr parseOperation(List<Token> tokens, IntegerRef n)
    {
        Token t = tokens.get(n.increment());
        if (t.getType() != TokenType.Name)
            return null;

        OpType type = OpType.valueOf(t.getText());

        if (tokens.get(n.increment()).getType() != TokenType.OpenBracket)
            return null;

        List<BoolExpr> args = new ArrayList<>();
        BoolExpr arg = this.parseExpr(tokens, n);
        while (arg != null && tokens.get(n.get()).getType() == TokenType.Comma)
        {
            n.increment();
            args.add(arg);
            arg = this.parseExpr(tokens, n);
        }
        args.add(arg);

        if (tokens.get(n.increment()).getType() != TokenType.CloseBracket)
            return null;

        switch (type)
        {
            case And: return new BoolAndExpr(args);
            case Or: return new BoolOrExpr(args);
            default:
                return null;
        }
    }

    BoolExpr parseVar(List<Token> tokens, IntegerRef n)
    {
        BoolExpr expr;
        boolean inverted = tokens.get(n.get()).getType() == TokenType.Neg;

        if (inverted)
            n.increment();

        Token t = tokens.get(n.increment());
        if (t.getType() == TokenType.Name)
        {
            String name = t.getText();
            Integer index;

            t = tokens.get(n.get());
            if (t.getType() == TokenType.Index)
            {
                n.increment();
                index = Integer.parseInt(t.getText().substring(1, t.getText().length() - 1));
            }
            else
            {
                index = null;
            }

            expr = new BoolVarExpr(name, index, inverted);
        }
        else
        {
            expr = null;
        }

        return expr;
    }
}
