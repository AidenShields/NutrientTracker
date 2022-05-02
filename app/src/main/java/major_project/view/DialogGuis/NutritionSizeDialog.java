package major_project.view.DialogGuis;

import javafx.scene.control.ChoiceDialog;
import major_project.model.API.Objects.Measure;

import java.util.ArrayList;
import java.util.Optional;

public class NutritionSizeDialog {
    public static Measure buildDialog(String title, String header, String content, ArrayList<Measure> measures){
        ChoiceDialog<Measure> dialog = new ChoiceDialog<>(measures.get(0), measures);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);

        Optional<Measure> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        }
        return measures.get(0);
    }
}
