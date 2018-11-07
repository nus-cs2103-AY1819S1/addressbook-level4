package seedu.souschef.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.ui.JumpToListRequestEvent;
import seedu.souschef.commons.events.ui.MealPlanPanelSelectionChangedEvent;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.healthplan.Scheme;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.planner.Meal;
import seedu.souschef.model.recipe.Recipe;


/**
 *  controller class for health plan details
 */
public class HealthPlanDetailsPanel extends GenericListPanel<HealthPlan> {

    private static final String FXML = "HealthPlanDetailsPanel.fxml";
    private static final String BAR_CHART_TARGET_LABEL = "Target";
    private static final String BAR_CHART_CONSUMED_LABEL = "Consumed";

    @FXML
    protected NumberAxis xAxis;
    @FXML
    protected CategoryAxis yAxis;

    @FXML
    protected BarChart<Number, String> chart;

    @FXML
    protected ListView<Day> daysListView;

    @FXML
    protected Label planName;

    @FXML
    protected Label targetToMeet;

    @FXML
    protected Label consumed;

    @FXML
    protected Label percentage;


    private double totalCalFromDays = 0.0;
    private HashMap <String, Double> calorieLibrary;
    private ObservableList<Recipe> recipes;
    private final Logger logger = LogsCenter.getLogger(HealthPlanDetailsPanel.class);

    //temporary measure key: recipe Name, list holds ingredients to map to calorie Library

    public HealthPlanDetailsPanel (ObservableList<HealthPlan> plan, ObservableList<Recipe> recipes, int index) {
        super(FXML);
        this.calorieLibrary = new HashMap<>();

        this.recipes = recipes;
        index = index - 1;
        ArrayList<HealthPlan> temp = new ArrayList<>();
        temp.add(plan.get(index));
        preloadLibrary();
        ObservableList<HealthPlan> targetPlan = FXCollections.observableArrayList(temp);
        setConnections(targetPlan);
        planName.setText(plan.get(index).getHealthPlanName().planName);


        registerAsAnEventHandler(this);
    }
    @Override
    protected void setConnections(ObservableList<HealthPlan> plan) {

        ObservableList<Day> days = FXCollections.observableArrayList(plan.get(0).getMealPlans());
        double total = mapCalories(plan.get(0).getMealPlans());
        double target = determineTarget(plan.get(0).getScheme());

        //prep bar chart labels
        xAxis.setLabel("Calories");
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Comparison");

        XYChart.Series series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data(BAR_CHART_TARGET_LABEL, target));
        series1.getData().add(new XYChart.Data(BAR_CHART_CONSUMED_LABEL, total));
        chart.getData().add(series1);

        //handle labels
        targetToMeet.setText(target + " kcals");

        //handle labels
        consumed.setText(total + " kcals");

        //rounding to 2 decimal places
        double percentageCalculated = Math.round(((total / target) * 100.0) * 100.0) / 100.0;
        percentage.setText(String.valueOf(percentageCalculated) + "%");

        daysListView.setItems(days);
        daysListView.setCellFactory(listView -> new DayListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    @Override
    protected void setEventHandlerForSelectionChangeEvent() {
        daysListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in day list panel changed to : '" + newValue + "'");
                        raise(new MealPlanPanelSelectionChangedEvent(newValue));
                    }
                });
    }


    @Override
    protected void scrollTo(int index) {
        Platform.runLater(() -> {
            daysListView.scrollTo(index);
            daysListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Override
    @Subscribe
    protected void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     *  mapping calories for the daily intake
     * @param days
     * @return
     */
    private double mapCalories(ArrayList<Day> days) {
        //given a list of days,
        //iterate through the list to get
        for (int i = 0; i < days.size(); i++) {
            if (!days.get(i).getMeal(Meal.Slot.BREAKFAST).isEmpty()) {
                Recipe recipe =
                        recipeMatching(days.get(i).getMeal(Meal.Slot.BREAKFAST).getRecipe().getName().fullName.trim());
                recipe.getIngredients().forEach((def, port)-> {
                    if (calorieLibrary.containsKey(def.getName().toString().toLowerCase().trim())) {
                        totalCalFromDays += this.calorieLibrary.get(def.getName().toString().toLowerCase().trim());
                    }
                });
            }
            if (!days.get(i).getMeal(Meal.Slot.LUNCH).isEmpty()) {
                Recipe recipe =
                        recipeMatching(days.get(i).getMeal(Meal.Slot.LUNCH).getRecipe().getName().fullName.trim());
                recipe.getIngredients().forEach((def, port)-> {
                    if (calorieLibrary.containsKey(def.getName().toString().toLowerCase().trim())) {
                        totalCalFromDays += this.calorieLibrary.get(def.getName().toString().toLowerCase().trim());
                    }
                });
            }
            if (!days.get(i).getMeal(Meal.Slot.DINNER).isEmpty()) {
                Recipe recipe =
                        recipeMatching(days.get(i).getMeal(Meal.Slot.DINNER).getRecipe().getName().fullName.trim());
                recipe.getIngredients().forEach((def, port)-> {
                    if (calorieLibrary.containsKey(def.getName().toString().toLowerCase().trim())) {
                        totalCalFromDays += this.calorieLibrary.get(def.getName().toString().toLowerCase().trim());
                    }
                });
            }
        }
        return totalCalFromDays;
    }

    /**
     *  populate calory library
     */
    private void preloadLibrary() {

        this.calorieLibrary.put("Carrot".toLowerCase().trim(), 50.0);
        this.calorieLibrary.put("Chicken".toLowerCase().trim(), 100.0);
        this.calorieLibrary.put("Potato".toLowerCase().trim(), 150.0);
        this.calorieLibrary.put("Onion".toLowerCase().trim(), 30.0);
        this.calorieLibrary.put("Egg".toLowerCase().trim(), 250.0);
        this.calorieLibrary.put("Garlic".toLowerCase().trim(), 40.0);
        this.calorieLibrary.put("Salt".toLowerCase().trim(), 5.0);
        this.calorieLibrary.put("BlackPepper".toLowerCase().trim(), 5.0);
        this.calorieLibrary.put("BreadCrumbs".toLowerCase().trim(), 15.0);
        this.calorieLibrary.put("OliveOil".toLowerCase().trim(), 100.0);
        this.calorieLibrary.put("Dashi".toLowerCase().trim(), 100.0);
        this.calorieLibrary.put("Flour".toLowerCase().trim(), 100.0);
        this.calorieLibrary.put("Ghee".toLowerCase().trim(), 300.0);
        this.calorieLibrary.put("Water".toLowerCase().trim(), 0.0);
        this.calorieLibrary.put("SoySauce".toLowerCase().trim(), 50.0);
        this.calorieLibrary.put("Oil".toLowerCase().trim(), 10.0);
        this.calorieLibrary.put("Octopus".toLowerCase().trim(), 30.0);
        this.calorieLibrary.put("GreenOnion".toLowerCase().trim(), 20.0);
        this.calorieLibrary.put("Ginger".toLowerCase().trim(), 10.0);
        this.calorieLibrary.put("Mayo".toLowerCase().trim(), 50.0);
        this.calorieLibrary.put("DriedSeaweed".toLowerCase().trim(), 30.0);
        this.calorieLibrary.put("BonitoFlakes".toLowerCase().trim(), 40.0);
    }

    /**
     * method to determine the target to hit
     */
    private double determineTarget(Scheme scheme) {
        double target = 0.0;
        if (scheme.equals(Scheme.GAIN)) {
            target = 3000.0;
        } else if (scheme.equals(Scheme.MAINTAIN)) {
            target = 2500.0;
        } else if (scheme.equals(Scheme.LOSS)) {
            target = 2000.0;
        }
        return target;

    }

    /**
     * method to find the recipe based on name given
     */
    private Recipe recipeMatching(String recipeName) {

        Recipe recipe = null;
        for (Recipe r : recipes) {
            if (r.getName().fullName.trim().equals(recipeName)) {
                recipe = r;
                break;
            }
        }
        return recipe;

    }

    /**
     *  cell implementation to handle the day listfor health plans details
     */
    class DayListViewCell extends ListViewCell<Day> {
        @Override
        protected void updateItem(Day day, boolean empty) {
            super.updateItem(day, empty);

            if (empty || day == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MealPlanCard(day, getIndex() + 1).getRoot());
            }
        }
    }

}
