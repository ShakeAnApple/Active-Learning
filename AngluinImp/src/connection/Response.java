package connection;

import java.util.List;
import java.util.UUID;

public class Response {
    List<ResponseQueryItem> _responseSeq;

    private UUID _id;

    public Response(UUID id, List<ResponseQueryItem> subresponses){
        _responseSeq = subresponses;
        _id = id;
    }

    public Response(List<ResponseQueryItem> subresponses){
        _responseSeq = subresponses;
        _id = UUID.randomUUID();
    }

    public List<ResponseQueryItem> getResponseSequence() {
        return _responseSeq;
    }

    public SystemState getResultSystemState(){
        return _responseSeq.get(_responseSeq.size() - 1).getOutputSystemState();
    }

    public boolean startsWith(Response resp){
        List<ResponseQueryItem> respSeq = resp.getResponseSequence();

        if (respSeq.size() > _responseSeq.size()){
            return false;
        }

        for (int i = 0; i < respSeq.size(); i++) {
            if (!respSeq.get(i).getRequest().getSymbol().equals(_responseSeq.get(i).getRequest().getSymbol())){
                return false;
            }
        }

        return true;
    }

    public UUID getId() {
        return _id;
    }

}
