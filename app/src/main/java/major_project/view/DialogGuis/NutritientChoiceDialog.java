package major_project.view.DialogGuis;

import javafx.scene.control.ChoiceDialog;
import major_project.model.API.Objects.Measure;
import major_project.model.API.Objects.Nutritents2ElectricBoogaloo;

import java.util.ArrayList;
import java.util.Optional;

public class NutritientChoiceDialog {
    public static String buildDialog(String title, String header, String content, ArrayList<String> nutrients){

        ChoiceDialog<String> dialog = new ChoiceDialog<>(nutrients.get(0), nutrients);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        }
        return nutrients.get(0);
    }
}
