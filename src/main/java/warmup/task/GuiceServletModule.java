package warmup.task;

/**
 * Created by vladimir on 5/30/16.
 */

import com.google.inject.servlet.ServletModule;

public class GuiceServletModule extends ServletModule {

    @Override
    protected void configureServlets() {
        bind(ApiInterface.class).to(ApiInterfaceImpl.class);
    }
}