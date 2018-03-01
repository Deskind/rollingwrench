package com.deskind.rollingwrench.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.deskind.rollingwrench.dao.CarsDAO;
import com.deskind.rollingwrench.database.AppDatabase;
import com.deskind.rollingwrench.database.DBUtility;
import com.deskind.rollingwrench.entities.Car;
import com.deskind.rollingwrench.entities.FluidService;
import com.deskind.rollingwrench.entities.FuelUp;
import com.deskind.rollingwrench.entities.Repair;
import com.deskind.rollingwrench.managers.MileageManager;
import com.deskind.rollingwrench.managers.Toster;
import com.deskind.rollingwrench.utils.Tokenizer;
import com.rollingwrench.deskind.rollingwrench.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Variables
    public static AlertDialog dialog = null;
    public static ArrayAdapter<String> adapter;
    public static String newCarBrand = null;

    //Views
    public static Spinner spinner = null;
    public static Context context;
    public TextView fuelSpendings, repairSpendings, fluidsSpendings;

    @Override
    protected void onStart(){
        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        context = getApplicationContext();
        spinner = (Spinner)findViewById(R.id.car_spinner);

        fuelSpendings = (TextView)findViewById(R.id.fuelSpengings);
        repairSpendings = (TextView)findViewById(R.id.repairSpendings);
        fluidsSpendings = (TextView)findViewById(R.id.fluidsSpendings);

        //Prepare spinner
        spinner.setAdapter(prepareSpinner(DBUtility.getAppDatabase(getApplicationContext())));
        spinner.setOnItemSelectedListener(new SpinnerItem());

        if(spinner.getCount() == 0){
//            Toast.makeText(this, "Введите имя для вашего авто", Toast.LENGTH_LONG).show();
//            startActivity(new Intent(this, NewCarActivity.class));

            //Fill app with some data
            CarsDAO dao = DBUtility.getAppDatabase(this).getCarsDao();
            //cars
            dao.insertNewCar(new Car("Audi a4 b7"));
            dao.insertNewCar(new Car("BMW e60"));

            //repairs
            //#1
            dao.insertRepair(new Repair("Audi a4 b7", "2012-01-18", 158500, "Mintex",
                    "------", "Тормозные колодки(перед)", 100));
            //#2
            dao.insertRepair(new Repair("Audi a4 b7", "2012-03-20", 160000, "---",
                    "------", "Ремонт блока комфорта. Вынесен из ниши под рулевую колонку для защиты от влаги", 75));
            //#3
            dao.insertRepair(new Repair("Audi a4 b7", "2012-07-17", 171200, "Audi",
                    "------", "Датчик давления фреона. БУ", 35));
            //#4
            dao.insertRepair(new Repair("Audi a4 b7", "2012-10-15", 179500, "---",
                    "------", "Тормозные колодки (зад)", 100));
            //#5
            dao.insertRepair(new Repair("Audi a4 b7", "2013-04-09", 192000, "Dayco, INA",
                    "------", "Комлект ремня грм (2ролика(INA), ремень(DAYCO), помпа(DOLZ)", 143));
            //#6
            dao.insertRepair(new Repair("Audi a4 b7", "2013-04-09", 192000, "Dayco",
                    "------", "Ремень вспомогательных механизмов +  натяжитель", 39));
            //#7
            dao.insertRepair(new Repair("Audi a4 b7", "2013-05-23", 196000, "NK",
                    "------", "Тормозные колодки, перед", 30));
            //#8
            dao.insertRepair(new Repair("Audi a4 b7", "2013-05-29", 196500, "AS Metal",
                    "------", "Два верхних рычага(перед, сторона пассажира)", 50));
            //#9
            dao.insertRepair(new Repair("Audi a4 b7", "2013-05-29", 196500, "Sipem",
                    "------", "Рулевая тяга(Сторона пассажира)", 15));
            //#10
            dao.insertRepair(new Repair("Audi a4 b7", "2013-09-05", 204200, "ZEN",
                    "------", "Замена бендикса", 35));
            //#11
            dao.insertRepair(new Repair("Audi a4 b7", "2014-07-02", 225000, "Febi",
                    "------", "Тормозные диски (перед)", 70));
            //#12
            dao.insertRepair(new Repair("Audi a4 b7", "2014-07-02", 225000, "Trusting",
                    "------", "Тормозные колодки (перед)", 30));
            //#13
            dao.insertRepair(new Repair("Audi a4 b7", "2014-07-02", 225000, "Trusting",
                    "------", "Тормозные колодки (зад)", 15));
            //#14
            dao.insertRepair(new Repair("Audi a4 b7", "2014-08-15", 230000, "Ruvillie",
                    "------", "Замена верхнего переднего рычага (сторона пассажира)", 30));
            //#15
            dao.insertRepair(new Repair("Audi a4 b7", "2014-10-09", 235500, "Audi",
                    "------", "Датчик педали сцепления (оригинал)", 20));

            //My repairs
            //#16
            dao.insertRepair(new Repair("Audi a4 b7", "2017-03-05", 246500, "Gates",
                    "------", "Комлект ремня грм (2ролика, ремень, помпа)", 100));
            //#17
            dao.insertRepair(new Repair("Audi a4 b7", "2017-03-05", 246500, "INA",
                    "------", "Паразитный ролик вспомогательных механизмов", 15));
            //#18
            dao.insertRepair(new Repair("Audi a4 b7", "2017-11-11", 264500, "SACHS",
                    "------", "Пыльники и отбойники задних амортизаторов", 20));
            //#19
            dao.insertRepair(new Repair("Audi a4 b7", "2017-11-11", 264500, "TRW",
                    "------", "Два рычага передней подвески(справа вверху)", 80));
            //#20
            dao.insertRepair(new Repair("Audi a4 b7", "2017-11-11", 264500, "TRW",
                    "------", "Сайлент блоки. Два в рычагах спереди-слева-вверху. Четыре сайлентблока в прямых нижних рычагах", 35));
            //#21
            dao.insertRepair(new Repair("Audi a4 b7", "2017-11-11", 264500, "Audi",
                    "------", "Поворотный кулак (правый). Сломалось крепление верхних рычагов. Куплен на разборке", 80));
            //#22
            dao.insertRepair(new Repair("Audi a4 b7", "2017-04-03", 248500, "BOSH",
                    "------", "Втягивающее реле стартера", 35));
            //#23
            dao.insertRepair(new Repair("Audi a4 b7", "2017-05-06", 250000, "Audi",
                    "------", "Блок бортовой сети. БУ.", 40));
            //#24
            dao.insertRepair(new Repair("Audi a4 b7", "2017-09-03", 261000, "Audi",
                    "------", "Датчик давления фреона(оригинал)", 45));
            //#25
            dao.insertRepair(new Repair("Audi a4 b7", "2017-12-01", 266100, "SKF",
                    "------", "Натяжитель вспомогательных механизмов. Можно было не менять, дело было в обгонной муфте", 35));
            //#26
            dao.insertRepair(new Repair("Audi a4 b7", "2017-12-01", 266100, "Valeo",
                    "------", "Обгонная муфта генератора", 30));
            //#27
            dao.insertRepair(new Repair("Audi a4 b7", "2017-12-30", 266700, "Ate",
                    "602886", "Тормозные колодки(перед)", 40));
            //#28
            dao.insertRepair(new Repair("Audi a4 b7", "2017-12-30", 266700, "Ate",
                    "607110", "Тормозные колодки(зад)", 20));
            //#29
            dao.insertRepair(new Repair("Audi a4 b7", "2017-12-30", 266700, "Ate",
                    "410261", "Тормозные диски(зад)", 40));


            dao.insertRepair(new Repair("BMW e60", "2016-12-03", 217000, "Febi",
                    "123456", "Нижний рычаг, левая сторона", 36));
            dao.insertRepair(new Repair("BMW e60", "2016-12-04", 217500, "Febi",
                    "123456", "Нижний рычаг, правая сторона сторона", 36));
            dao.insertRepair(new Repair("BMW e60", "2016-12-13", 221000, "Lemferder",
                    "123456", "Рулевой наконечник левый", 41));
            dao.insertRepair(new Repair("BMW e60", "2016-12-14", 224000, "Lemferder",
                    "123456", "Рулевой наконечник, правый", 35));


            //fluids
            //#1
            dao.insertFluidService(new FluidService("Audi a4 b7", "2011-06-10", 143350, "Petronas",
                    0, "Моторное масло 5w30"));
            //#2
            dao.insertFluidService(new FluidService("Audi a4 b7", "2012-11-01", 158000, "Sunoco",
                    0, "Моторное масло 5w30"));
            //#3
            dao.insertFluidService(new FluidService("Audi a4 b7", "2012-09-03", 174400, "Sunoco",
                    0, "Моторное масло 5w30"));
            //#4
            dao.insertFluidService(new FluidService("Audi a4 b7", "2012-12-29", 185000, "Sunoco",
                    0, "Моторное масло 5w30"));
            //#5
            dao.insertFluidService(new FluidService("Audi a4 b7", "2013-05-08", 195000, "Sunoco",
                    0, "Моторное масло 5w30"));
            //#6
            dao.insertFluidService(new FluidService("Audi a4 b7", "2013-10-16", 207000, "Sunoco",
                    0, "Моторное масло 5w30"));
            //#7
            dao.insertFluidService(new FluidService("Audi a4 b7", "2014-03-15", 218000, "Kroon-Oil",
                    0, "Моторное масло 5w30"));
            //#8
            dao.insertFluidService(new FluidService("Audi a4 b7", "2014-08-15", 230000, "Kroon-Oil",
                    0, "Моторное масло 5w30"));
            //#9
            dao.insertFluidService(new FluidService("Audi a4 b7", "2015-05-05", 244000, "Kroon-Oil",
                    0, "Моторное масло 5w30"));
            //#10
            dao.insertFluidService(new FluidService("Audi a4 b7", "2017-03-05", 246500, "Wolf",
                    35, "Моторное масло 5w30"));
            //#11
            dao.insertFluidService(new FluidService("Audi a4 b7", "2017-03-05", 246500, "Febi",
                    8, "Антифриз G12+ (розовый, концентрат)"));
            //#12
            dao.insertFluidService(new FluidService("Audi a4 b7", "2017-07-29", 255900, "Wolf",
                    35, "Моторное масло 5w30"));
            //#13
            dao.insertFluidService(new FluidService("Audi a4 b7", "2017-11-11", 264500, "Wolf",
                    35, "Моторное масло 5w30"));

            //fuel ups
            dao.insertFuelUp(new FuelUp("Audi a4 b7", "2017-01-01", 23, 26.17f));
            dao.insertFuelUp(new FuelUp("Audi a4 b7", "2017-01-08", 29, 31.56f));
            dao.insertFuelUp(new FuelUp("Audi a4 b7", "2017-01-14", 14, 16.56f));
            dao.insertFuelUp(new FuelUp("Audi a4 b7", "2017-01-26", 34, 40.18f));

        }


    }

    @Override
    protected void onResume(){
        super.onResume();

        Log.i("RESUME", "CALLED");
        //Check on new car in the app
        if(NewCarActivity.isThereNewCar){
            spinner.setAdapter(prepareSpinner(DBUtility.getAppDatabase(this)));
            NewCarActivity.isThereNewCar = false;
        }

        String carBrand = (String)spinner.getSelectedItem();

        //Calculate spendings for every category
        String currency_token = Tokenizer.getToken(this);
        Log.i("CURRENCY TOKEN IS: ", currency_token);

        float forFuel = calcFuelSpendings(carBrand);
        fuelSpendings.setText(String.format("%.1f", forFuel)+currency_token);


        float forRepairs = calcRepairsSpendings(carBrand);
        repairSpendings.setText(String.format("%.1f", forRepairs)+currency_token);

        int fluidsTotalCost = calcFluidsSpendings(carBrand);
        fluidsSpendings.setText(String.valueOf(fluidsTotalCost)+currency_token);
    }

    public void fuelCategoryClicked(View v){
        if(MainActivity.spinner.getCount() != 0) {
            Intent intent = new Intent(this, FuelChooseActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Необходимо добавить авто", Toast.LENGTH_SHORT).show();
        }

    }

    public void fluidsCategoryClicked(View v){
        if(MainActivity.spinner.getCount() != 0) {
            Intent intent = new Intent(this, FluidsActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Необходимо добавить авто", Toast.LENGTH_SHORT).show();
        }
    }

    public void repairsCategotyClicked(View v){
        if(MainActivity.spinner.getCount() != 0) {
            Intent intent = new Intent(this, RepairsCategoryActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Необходимо добавить авто", Toast.LENGTH_SHORT).show();
        }
    }

    public void addNewCarClicked(View v){
        Intent intent = new Intent(this, NewCarActivity.class);
        startActivity(intent);
    }


    private ArrayAdapter prepareSpinner(AppDatabase database){
        String [] arr = database.getCarsDao().getAllCarBrands();
        Log.i("DB", "CARS IN DATABASE " + arr.length);
        adapter =  new ArrayAdapter<String>(context, R.layout.spinner_item, arr);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        return adapter;
    }

    public static float calcFuelSpendings(String carBrand){
        float fuelSpendings = 0;

        List<FuelUp> fuelUps = DBUtility.getAppDatabase(context).getCarsDao().getFuelUps();
        for(FuelUp fuelUp : fuelUps){
            if(fuelUp.getCarBrand().equals((String)MainActivity.spinner.getSelectedItem())){
                fuelSpendings+=fuelUp.getCost();
            }
        }

        return fuelSpendings;
    }

    public static float calcRepairsSpendings(String carBrand){
        float repairsSpendings = 0;
        Repair[] repairs = DBUtility.getAppDatabase(context).getCarsDao().getAllRapairsForBrand(carBrand);
        ArrayList<Repair> repairs1 = new ArrayList<>(Arrays.asList(repairs));
        for(Repair r : repairs1){
            repairsSpendings+=r.getPartPrice();
        }
        return repairsSpendings;
    }

    public static int calcFluidsSpendings(String carBrand){
        int total = 0;
        int [] arr = DBUtility.getAppDatabase(context).getCarsDao().getFluidServicesTotalCost(carBrand);
        for(int i = 0 ; i < arr.length; i++){
            total+=arr[i];
        }

        return total;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get shared preferences and editor
        SharedPreferences preferences = getSharedPreferences(getString(R.string.shared_preferences_file), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = null;

        switch (item.getItemId()){
            case R.id.delete_car_menu_item:

                view = getLayoutInflater().inflate(R.layout.dialog_delete_car, null);

                builder.setView(view);
                dialog = builder.create();
                dialog.show();

                final EditText etCarBrand = (EditText)view.findViewById(R.id.etDeleteCar);
                Button button = (Button)view.findViewById(R.id.car_delete_button);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String carToDelete = etCarBrand.getText().toString();
                        Log.i("CARBRAND" , carToDelete);
                        if(carToDelete.isEmpty()){

                            Toast.makeText(getApplicationContext(), "Введите название авто", Toast.LENGTH_LONG).show();
                        }else{
                            String brand = (String)MainActivity.spinner.getSelectedItem();
                            Car car = DBUtility.getAppDatabase(getApplicationContext()).getCarsDao().getCar(carToDelete);
                            if(car==null){
                                Toast.makeText(getApplicationContext(), "Авто с таким названием не найдено", Toast.LENGTH_LONG).show();
                            }else {
                                DBUtility.getAppDatabase(getApplicationContext()).getCarsDao().deleteCar(carToDelete);
                                Toast.makeText(getApplicationContext(), "Авто удалено", Toast.LENGTH_LONG).show();

                                //Refresh values on the activity
                                spinner.setAdapter(prepareSpinner(DBUtility.getAppDatabase(getApplicationContext())));
                                float forFuel = calcFuelSpendings((String)MainActivity.spinner.getSelectedItem());
                                fuelSpendings.setText(String.format("%.1f", forFuel));
                                float forRepairs = calcRepairsSpendings((String)MainActivity.spinner.getSelectedItem());
                                repairSpendings.setText(String.format("%.1f", forRepairs));
                                int fluidsTotalCost = calcFluidsSpendings((String)MainActivity.spinner.getSelectedItem());
                                fluidsSpendings.setText(String.valueOf(fluidsTotalCost));

                                dialog.cancel();
                            }
                        }

                    }
                });

                return true;

                case R.id.set_current_mileage:
                    MileageManager.showCurrentMileageDialog(this, getLayoutInflater());
                    return true;

                case R.id.set_currency:

                    view = getLayoutInflater().inflate(R.layout.dialog_set_currency, null);
                    dialog = builder.setView(view).create();
                    dialog.show();
                    //find view with edit text to get text
                    final EditText currencyToken = view.findViewById(R.id.currency_token);
                    Button currencyTokenDone = view.findViewById(R.id.currenct_token_done);


                    //set up listener for button
                    currencyTokenDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String token = currencyToken.getText().toString();
                            if(token.equals("")){
                                Toster.showToast(getApplicationContext(), "Введите валюту", Toast.LENGTH_SHORT);
                            }else{
                                //write value to shared preferences file and commit
                                editor.putString("currency_token", token);
                                Tokenizer.setTokenizer(token);
                                editor.commit();
                                dialog.cancel();
                                fuelSpendings.setText(String.format("%.1f", calcFuelSpendings((String)spinner.getSelectedItem()))+token);
                                repairSpendings.setText(String.format("%.1f", calcRepairsSpendings((String)spinner.getSelectedItem()))+token);
                                fluidsSpendings.setText(String.valueOf(calcFluidsSpendings((String)spinner.getSelectedItem()))+token);
                            }
                        }
                    });

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    class SpinnerItem implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            String carBrand = (String)spinner.getSelectedItem();

            //reset current mileage
            MileageManager.setCurrentMileage(0);

            String token = Tokenizer.getToken(MainActivity.this);

            float forFuel = calcFuelSpendings(carBrand);
            fuelSpendings.setText(String.format("%.1f", forFuel)+token);

            float forRepairs = calcRepairsSpendings(carBrand);
            repairSpendings.setText(String.format("%.1f", forRepairs)+token);

            int forFluids = calcFluidsSpendings(carBrand);
            fluidsSpendings.setText(String.valueOf(forFluids)+token);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}
