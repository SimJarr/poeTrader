package se.simjarr.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import se.simjarr.global.Currency;
import se.simjarr.global.ThreadLocalVariables;
import se.simjarr.model.PostTradesUrlParameterBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestPanelLayout extends VerticalLayout {

    private ThreadLocalVariables threadLocalVariables;

    public TestPanelLayout() {
        threadLocalVariables = ((ApplicationUI) UI.getCurrent()).getThreadLocalVariables();

        addButton();
    }

    private void addButton() {
        Button button = new Button("Test");
        button.addClickListener(clickEvent ->  {
            try {

                String url = "http://currency.poe.trade/shop?league=Hardcore+Legacy";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String urlParameters = new PostTradesUrlParameterBuilder(threadLocalVariables.getSelectedLeague(), threadLocalVariables.getApikey())
                        .addTrade(Currency.CHAOS_ORB, 10, 4, Currency.CARTOGRAPHERS_CHISEL).build();

                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.addComponent(button);
    }
}




















