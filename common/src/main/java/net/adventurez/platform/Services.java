package net.adventurez.platform;

import java.util.ServiceLoader;

import net.adventurez.platform.services.INetworkHelper;
import net.adventurez.platform.services.IPlatformHelper;

public class Services {

    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    public static INetworkHelper network() {
        return NetworkHolder.NETWORK;
    }

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz, Services.class.getClassLoader()).findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }

    private static final class NetworkHolder {
        private static final INetworkHelper NETWORK = load(INetworkHelper.class);
    }
}
