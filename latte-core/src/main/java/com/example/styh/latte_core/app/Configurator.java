package com.example.styh.latte_core.app;


import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

public class Configurator {

    private static final HashMap<Object,Object> LATTE_CONFIGS = new HashMap<>();
//    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator(){
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);//配置开始，未完成
    }

    //单例：懒汉式
    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }
    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    final HashMap<Object,Object> getLatteConfigs(){
        return LATTE_CONFIGS;
    }

    public final void configure( ){
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);//配置完成
    }

    public final Configurator withApiHost(String host){
        LATTE_CONFIGS.put(ConfigType.API_HOST.name(),host);
        return this;
    }

    public final Configurator withLoaderDeLayed(long deLayed){
        LATTE_CONFIGS.put(ConfigType.LOADER_DELAYED, deLayed);
        return this;
    }

//    private void initICons() {
//        if (ICONS.size() > 0) {
//            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
//            for (int i = 1; i < ICONS.size(); i++) {
//                initializer.with(ICONS.get(i));
//            }
//        }
//    }
//
//    public Configurator withIcon(IconFontDescriptor descriptor) {
//        ICONS.add(descriptor);
//        LATTE_CONFIGS.put(ConfigType.ICON.name(),ICONS);
//        return this;
//    }

    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigType.INTERCEPTORS,INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigType.INTERCEPTORS,INTERCEPTORS);
        return this;
    }

    private void checkCofiguration(){
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady){
            throw new RuntimeException("Configuration is not ready,call cofigure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key){
        checkCofiguration();
        return (T) LATTE_CONFIGS.get(key.name());
    }
}
