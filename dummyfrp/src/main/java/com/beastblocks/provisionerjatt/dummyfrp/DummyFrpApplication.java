package com.beastblocks.provisionerjatt.dummyfrp;

import android.app.Application;
import com.beastblocks.provisionerjattsdkfrp.ProvisionerJatt;

public class DummyFrpApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ProvisionerJatt.initialize(this);
    }
}
