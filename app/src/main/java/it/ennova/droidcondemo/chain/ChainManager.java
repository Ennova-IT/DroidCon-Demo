package it.ennova.droidcondemo.chain;

public class ChainManager {
    private static ChainManager instance = new ChainManager();
    private final BaseRequestChain chain;

    public static ChainManager getInstance() {
        return instance;
    }

    private ChainManager() {
        chain = new RootRequest();
        BaseRequestChain photoListRequest = new PhotoListRequest();
        photoListRequest.setNext(new GetPhotoRequest());
        chain.setNext(photoListRequest);
    }

    public BaseRequestChain getChain() {
        return chain;
    }
}
