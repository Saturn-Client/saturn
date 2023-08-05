

package com.saturnclient.saturnclient.eventbus;

public interface IEventBus {
    void register(Object registerClass);

    void unregister(Object registerClass);

    void post(SaturnEvent event);
}
