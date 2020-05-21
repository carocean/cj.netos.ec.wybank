package cj.netos.ec.wybank;

public interface ITradeEventNotify {
    void onsuccess(String cmd, Object event,String toPerson) ;

    void onerror(String cmd, Object event,String toPerson);

}
