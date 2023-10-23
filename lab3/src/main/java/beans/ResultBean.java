package beans;

import database.ResultInterface;
import lombok.Data;
import lombok.Getter;

import javax.inject.Inject;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * This class represents a bean for managing and interacting with results.
 */
@Data
public class ResultBean implements Serializable {

    @Inject
    private ResultInterface resultInterface;

    private Result currResult;
    private List<Result> resultList;
    @Getter
    private String source;

    /**
     * Initializes the ResultBean by creating a new instance of Result and updating the local result list.
     */
    @PostConstruct
    private void initialize() {
        currResult = new Result();
        updateLocal();
    }

    /**
     * Updates the local result list by fetching results from the ResultInterface.
     */
    private void updateLocal() {
        resultList = resultInterface.getAll();
    }

    /**
     * Adds the current result to the result list, including a timestamp of the request time.
     */
    public void addResult() {
        Result copyResult = new Result(currResult);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String requestTime = dateFormat.format(new Date(System.currentTimeMillis()));
        copyResult.setRequestTime(requestTime);
        resultInterface.save(copyResult);
        updateLocal();
    }

    /**
     * Clears all results in the resultInterface and updates the local result list.
     */
    public void clearResults() {
        resultInterface.clear();
        resultList = resultInterface.getAll();
        updateLocal();
    }

    /**
     * Sets the source attribute.
     *
     * @param source The source to be set.
     */
    public void setSource(String source) {
        this.source = source;
    }
}
