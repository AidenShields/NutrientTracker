package major_project.view.DialogGuis;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class TextInput {
    public static String getTextInput(String title, String header, String contextText){
        TextInputDialog box = new TextInputDialog();
        box.setHeaderText(header);
        box.setContentText(contextText);
        Optional<String> results = box.showAndWait();
        if(results.isPresent()){
            return results.get();
        }
        return "";
    }
}
