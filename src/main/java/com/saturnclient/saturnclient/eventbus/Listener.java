

package com.saturnclient.saturnclient.eventbus;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public final class Listener {
    private final Object listenerClass;
    private final Method method;
    private final Consumer<SaturnEvent> lambda;

    public Listener(final Object listenerClass, final Method method, final Consumer<SaturnEvent> lambda) {
        this.listenerClass = listenerClass;
        this.method = method;
        this.lambda = lambda;
    }

    public Listener(final Object listenerClass, final Method method) {
        this(listenerClass, method, null);
    }

    /**
     * Gets the method of the listener.
     *
     * @return method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Gets the lambda of the listener.
     *
     * @return lambda
     */
    public Consumer<SaturnEvent> getLambda() {
        return lambda;
    }

    /**
     * Gets the listener class.
     *
     * @return listener class
     */
    public Object getListenerClass() {
        return listenerClass;
    }

}
