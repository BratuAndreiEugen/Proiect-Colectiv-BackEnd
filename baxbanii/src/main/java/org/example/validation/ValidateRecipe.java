package org.example.validation;

import lombok.AllArgsConstructor;
import org.example.data.entity.Recipe;
import org.example.exceptions.DataChangeException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@AllArgsConstructor
@Component
public class ValidateRecipe {

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private boolean isValidDate(String date) {
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$"; // Format: yyyy-MM-dd
        Pattern pattern = Pattern.compile(dateRegex);
        return pattern.matcher(date).matches();
    }

    public void validateRecipe(Recipe recipe) throws DataChangeException {
        if (!isNotEmpty(recipe.getTitle()) ||
                !isNotEmpty(recipe.getCaption()) ||
                !isNotEmpty(recipe.getThumbnailLink())) {
            throw new DataChangeException("All fields must be filled!");
        }

        if (!isValidDate(recipe.getUploadDate())) {
            throw new DataChangeException("Upload date is not in the correct format (yyyy-MM-dd)!");
        }
    }
}
