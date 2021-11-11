package repository.file;

import domain.Entity;
import repository.memory.InMemoryRepository;
import validator.Validator;

import java.io.*;

/**
 * In file repository which stores data inside files and memory
 * @param <ID> generic id
 * @param <EType> generic type which holds id of type ID
 */
public abstract class InFileRepository<ID, EType extends Entity<ID>> extends InMemoryRepository<ID, EType> {

    private final String fileName;

    /**
     * Creates an instance of InFileRepository, requires a validator and a file name from which the data will be read
     * @param validator validator specific to the class which the repository stores
     * @param fileName valid file path from which the data will be read
     */
    public InFileRepository(Validator<EType> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        readFromFile();
    }

    /**
     * Parses the entity into a String
     * @param entity entity to be parsed
     * @return parsed entity
     */
    protected abstract String createEntityAsString(EType entity);

    /**
     * Parses the string and creates an entity
     * @param string string to be parsed
     * @return created entity
     */
    protected abstract EType createEntityFromString(String string);

    private void writeAllToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.fileName, false))) {
            Iterable<EType> entities = findAll();
            for (EType entity : entities) {
                bufferedWriter.write(createEntityAsString(entity));
                bufferedWriter.newLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void writeToFile(EType entity) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.fileName, true))) {
            bufferedWriter.write(createEntityAsString(entity));
            bufferedWriter.newLine();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void readFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    super.save(createEntityFromString(line));
                } catch (RuntimeException ignored) {}
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void save(EType entity) {
        try {
            super.save(entity);
            writeToFile(entity);
        }
        catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void remove(ID id) {
        super.remove(id);
        writeAllToFile();
    }

    @Override
    public Iterable<EType> findAll() {
        readFromFile();
        return super.findAll();
    }
}
