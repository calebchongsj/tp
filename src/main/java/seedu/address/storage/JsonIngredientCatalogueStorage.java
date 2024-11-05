package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.util.SampleDataUtil;

public class JsonIngredientCatalogueStorage implements IngredientCatalogueStorage {

    private final Path filePath;
    private static final Logger logger = LogsCenter.getLogger(JsonIngredientCatalogueStorage.class);

    public JsonIngredientCatalogueStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getIngredientCatalogueFilePath() {
        return filePath;
    }

    @Override
    public Optional<IngredientCatalogue> readIngredientCatalogue() throws DataLoadingException {
        return readIngredientCatalogue(filePath);
    }

    @Override
    public Optional<IngredientCatalogue> readIngredientCatalogue(Path filePath) throws DataLoadingException {
        if (!Files.exists(filePath)) {
            // Log here to confirm when the default sample data is used
            logger.info("Ingredient catalogue file not found. Initializing with sample data.");
            IngredientCatalogue sampleCatalogue = SampleDataUtil.getSampleIngredientCatalogue();
            try {
                saveIngredientCatalogue(sampleCatalogue, filePath);
            } catch (IOException e) {
                throw new DataLoadingException(e);
            }
            return Optional.of(sampleCatalogue);
        }
        return JsonUtil.readJsonFile(filePath, IngredientCatalogue.class);
    }


    @Override
    public void saveIngredientCatalogue(IngredientCatalogue ingredientCatalogue) throws IOException {
        saveIngredientCatalogue(ingredientCatalogue, filePath);
    }

    @Override
    public void saveIngredientCatalogue(IngredientCatalogue ingredientCatalogue, Path filePath) throws IOException {
        JsonUtil.saveJsonFile(ingredientCatalogue, filePath);
    }
}