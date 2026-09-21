package org.woonyong.behavior.runtime;

import org.woonyong.behavior.annotations.Action;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ActionInvoker {
    public static final String ACTION_INVOCATION_FAILED = "Failed to invoke @Action method: ";

    public static List<Method> findActionMethods(Object behaviorInstance) {
        List<Method> actions = new ArrayList<>();
        for (Method method : behaviorInstance.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Action.class)) {
                method.setAccessible(true);
                actions.add(method);
            }
        }
        return actions;
    }

    public static void invokeActions(Object behaviorInstance) {
        for (Method method : findActionMethods(behaviorInstance)) {
            try {
                method.invoke(behaviorInstance);
            } catch (Exception e) {
                throw new RuntimeException(ACTION_INVOCATION_FAILED + method.getName(), e);
            }
        }
    }
}
