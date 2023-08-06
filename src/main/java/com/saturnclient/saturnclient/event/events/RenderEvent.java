package com.saturnclient.saturnclient.event.events;

import com.saturnclient.saturnclient.event.Event;
import net.minecraft.client.util.math.MatrixStack;

public class RenderEvent extends Event {
    protected float partialTicks;
    protected MatrixStack matrixStack;

    public RenderEvent(float partialTicks, MatrixStack matrixStack) {
        this.partialTicks = partialTicks;
        this.matrixStack = matrixStack;
    }

    /**
     * Gets the partial ticks.
     *
     * @return partial ticks
     */
    public float getPartialTicks() {
        return partialTicks;
    }

    /**
     * Gets the matrix stack.
     *
     * @return matrix stack
     */
    public MatrixStack getMatrixStack() {
        return matrixStack;
    }

    /**
     * Types of render events.
     */
    public static class Post extends RenderEvent {
        public Post(float partialTicks, MatrixStack matrixStack) {
            super(partialTicks, matrixStack);
        }
    }

    public static class Pre extends RenderEvent {
        public Pre(float partialTicks, MatrixStack matrixStack) {
            super(partialTicks, matrixStack);
        }
    }

}