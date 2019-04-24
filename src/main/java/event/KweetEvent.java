package event;

import domain.Kweet;

public class KweetEvent {

    private Kweet kweet;

    public KweetEvent(Kweet kweet) {
        this.kweet = kweet;
    }

    public Kweet getKweet() {
        return kweet;
    }

    public void setKweet(Kweet kweet) {
        this.kweet = kweet;
    }
}
