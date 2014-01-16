package com.github.wiliamsouza.bluetooth;

import java.io.IOException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class BluetoothTest extends UiAutomatorTestCase {

    public void setUp() throws UiObjectNotFoundException, IOException {
        getUiDevice().pressHome();
        String packageName = "com.android.settings";
        String component = packageName + "/.Settings";
        String action = "am start -a android.intent.action.MAIN -n ";

        // start settings application
        Runtime.getRuntime().exec(action + component);

        UiObject settingsUi = new UiObject(new UiSelector().packageName(packageName));
        assertTrue("Application settings not started", settingsUi.waitForExists(5000));
    }

    public void testBluetooth() throws UiObjectNotFoundException {
        UiScrollable scroll = new UiScrollable(new UiSelector().scrollable(true));
        UiObject layout = scroll.getChildByText(new UiSelector().className(android.widget.LinearLayout.class.getName()),"Bluetooth", true);
        UiObject switchBluetooth = layout.getChild(new UiSelector().className(android.widget.Switch.class.getName()));
        assertTrue("Unable to find bluetooth switch object", switchBluetooth.exists());
        switchTo(switchBluetooth, "Bluetooth");
    }

    private void switchTo(UiObject switchObject, String name) throws UiObjectNotFoundException {
        // Before start test ensure switch is off
        if (switchObject.isChecked()) {
            switchObject.click();
            sleep(3000);
        }
        
        switchObject.click();
        sleep(3000);
       	assertTrue("Unable to turn " + name + " on", switchObject.isChecked());
       	
    	switchObject.click();
        sleep(3000);
       	assertFalse("Unable to turn " + name + " off", switchObject.isChecked());
    }
}
