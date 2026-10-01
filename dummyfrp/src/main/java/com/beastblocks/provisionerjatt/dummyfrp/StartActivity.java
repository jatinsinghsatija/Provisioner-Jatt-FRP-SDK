package com.beastblocks.provisionerjatt.dummyfrp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import com.beastblocks.provisionerjattsdkfrp.FrpSetResult;
import com.beastblocks.provisionerjattsdkfrp.ProvisionerJatt;
import com.beastblocks.provisionerjattsdkfrp.ProvisionerJattFrp;

public class StartActivity extends ComponentActivity {
    @Nullable
    private String lastFrpToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ProvisionerJatt.get();
        setContentView(R.layout.activity_start);
        refreshDeviceOwner();
        findViewById(R.id.btn_set_account_frp).setOnClickListener(v -> setAccountFrp());
        findViewById(R.id.btn_set_frp).setOnClickListener(v -> setFrp());
        findViewById(R.id.btn_set_organization_name).setOnClickListener(v -> setOrganizationName());
    }

    private void refreshDeviceOwner() {
        TextView status = findViewById(R.id.device_owner_status);
        boolean owner = ProvisionerJatt.isDeviceOwner(this);
        status.setText(owner ? R.string.device_owner_yes : R.string.device_owner_no);
    }

    private void setAccountFrp() {
        ProvisionerJattFrp.addFRPAccount(this, result -> {
            String message;
            if (result.getSuccess()) {
                lastFrpToken = result.getFrpToken();
                message = getString(
                    R.string.frp_account_success,
                    result.getName() == null ? "" : result.getName(),
                    result.getEmail() == null ? "" : result.getEmail(),
                    result.getFrpToken() == null ? "" : result.getFrpToken()
                );
            } else {
                message = getString(
                    R.string.frp_account_failed,
                    result.getReason() == null ? "" : result.getReason()
                );
            }
            runOnUiThread(() -> {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                refreshDeviceOwner();
            });
        }, getString(R.string.default_web_client_id));
    }

    private void setFrp() {
        if (lastFrpToken == null || lastFrpToken.isEmpty()) {
            Toast.makeText(this, R.string.frp_token_missing, Toast.LENGTH_LONG).show();
            return;
        }
        FrpSetResult result = ProvisionerJattFrp.setFRP(lastFrpToken, this);
        String message = result.getSuccess()
            ? getString(R.string.frp_set_success)
            : getString(R.string.frp_set_failed, result.getReason() == null ? "" : result.getReason());
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        refreshDeviceOwner();
    }

    private void setOrganizationName() {
        FrpSetResult result =
            ProvisionerJattFrp.setOrganizationName(getString(R.string.app_name), this);
        String message;
        if (result.getSuccess()) {
            message = getString(R.string.org_name_success, getString(R.string.app_name));
        } else {
            message = getString(
                R.string.org_name_failed,
                result.getReason() == null ? "" : result.getReason()
            );
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        refreshDeviceOwner();
    }
}
