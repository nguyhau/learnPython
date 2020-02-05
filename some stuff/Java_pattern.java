/*
 * Strategy Pattern
 **/
public absract class Vehicle
{
    public Vehicle() {}
    public void go() {
        System.out.println("Now I'm driving.");
    }
}
public class StreetRacer extends Vehicle
{
    public StreetRacer() {}
}
public Helicopter extends Vehicle
{
    public Helicopter() {}
    public void go () {
        System.out.println("Now I'm flying.");
    }    
}
public class StartTheRace
{
    public static void main(String[] args)
    {
        StreetRacer streeRacer = new StreetRacer();
        Helicopter helicopter = new Helicopter();
        streeRacer.go();
        helicopter.go();
    }
}
=================== Change "is a" to "has a"

public class StartTheRace
{
    public interface GoAlgorithm
    {
        public void go();
    }
    public class GoByDriving implements GoAlgorithm
    {
        public void go()
        {
            System.out.println("Now I'm driving");
        }
    }
    public class GoByFlying implements GoAlgorithm
    {
        public void go()
        {
            System.out.println("Now I'm flying");
        }
    }
    public abstract class Vehicle
    {
        private GoAlgorithm goAlgorithm;
        public Vehicle() {}
        public void setGoAlgorithm (GoAlgorithm algorithm)
        {
            goAlgorithm = algorithm;
        }
        public void go() 
        {
            goAlgorithm.go();
        }
    }
    public class StreetRacer extends Vehicle
    {
        public StreetRacer()
        {
            setGoAlgorithm(new GoByDriving());
        }
    }
    public class Helicopter extends Vehicle
    {
        public Helicopter()
        {
            setGoAlgorithm(new GoByFlying());
        }
    }
    public class Jet extends Vehicle
    {
        public Jet() {};
    }
    public static void main(String[] args)
    {
        DemoStrategy dm = new DemoStrategy();
        DemoStrategy.StreetRacer streetRacer = dm.new StreetRacer();
        DemoStrategy.Helicopter helicopter = dm.new Helicopter();
        streetRacer.go();
        helicopter.go();
        DemoStrategy.Jet jet = dm.new Jet();
        jet.setGoAlgorithm(dm.new GoByFlying());
        jet.go();
    }
}
/*
 * Decorator pattern
 *****************/
 
 public class DemoDecorator
 {
     public class Computer()
     {
         public Computer()
         {}
         public String description()
         {
             return "computer";
         }
     }
     public abstract class ComponentDecorator extends Computer
     {
         public abstract String description();
     }
     public class Disk extends ComponentDecorator
     {
         Computer computer;
         public Disk(Computer c)
         {
             computer = c;
         }
         public String description()
         {
             return computer.description() + "and a disk";
         }
     }
     public class CD extends ComponentDecorator
     {
         Computer computer;
         public CD(Computer c)
         {
             computer = c;
         }
         public String description()
         {
             return computer.description() + "and a CD";
         }
     }
     public class Monitor extends ComponentDecorator {
         Computer computer;
         public Monitor(Computer c) {
         {
             computer = c;
         }
         public String description()
         {
             return computer.description() + "and a monitor";
         }
     }
     public static void main(String args[])
     
     {
         DemoDecorator dm = new DemoDecorator();
         DemoDecorator.Computer computer = dm.new Computer();
         computer = dm.new Disk(computer);
         computer = dm.new Monitor(computer);
         System.out.println("You're getting a "+ computer.description());
     }
 }
 /*
  * Factory pattern
  *****************/
 
 public class DemoFactory
 {
     public abstract class Connection
     {
         public Connection() {}
         public String description()
         {
             return "Generic";
         }
     }
     public class OracleConnection extends Connection
     {
         public OracleConnection()
         {}
         public String description()
         {
             return "Oracle";
         }
     }
     public class SqlServerConnection extends Connection
     {
         public SqlServerConnection()
         {
             
         }
         public String description()
         {
             return "Sql Server";
         }
     }
     public class MySqlConnection extends Connection
     {
         public MySqlConnection()
         {}
         public String description()
         {
             return "My Sql Server";
         }
         
         {}
         
     }
     
     public class FirstFactory
     {
        protected String type;
        public FirstFactory(String t)
        {
            type = t;
        }        
        public Connection createConnection()
        {
            if (type.equals("Oracle")) {
                return new OracleConnection();
            }
            else if (type.equals("SQL Server")) {
                return new SqlServerConnection();
            }
            else {
                return new MySqlConnection();
            }
        }
     }
     
     public static void main(String args[])
     {
         DemoFactory dm = new DemoFactory();
         DemoFactory.FirstFactory factory;
         factory = dm.new FirstFactory("Oracle");
         DemoFactory.Connection connection = factory.createConnection();
         System.out.println("You're connection with " + connection.description());
     }
 }
 /**
  * Observer pattern
  *******************/
 import java.util.*
 public class DemoObserver
 {
     public interface Observer
     {
         public void update(String operation, String record);
     }
     public class Archiver implements Observer
     {
         public Archiver()
         {             
         }
         public void update(String operation, String record)
         {
             System.out.println("The archiver says a " + operation + 
             " operation was performed on " + record);
         }
     }
     public class Client implements Observer
     {
         public Client()
         {}
         public void update(String operation, String record)
         {
            System.out.println("The clients says a " + operation +
            " operation was performed on " + record);
         }
     }
     public class Boss implements Observer
     {
         public Boss()
         {}
         public void update(String operation, String record)
         {
             System.out.println("The boss says a " + operation +
             " operation was performed on "+ record);
         }
     }
     public interface Subject
     {
         public void registerObserver(Observer o);
         public void removeObserver(Observer o);
         public void notifyObservers();
     }
     
     public class Database implements Subject
     {
         private Vector<Observer> observers;
         private String operation;
         private String record;
         public Database()
         {
             observers = new Vector<Observer>();             
         }
         public void registerObserver(Observer o)
         {
             observers.add(o);
         }
         public void removeObserver(Observer o)
         {
             observers.remove(o);
         }
         public void notifyObservers()
         {
             for (int loopIndex = 0; loopIndex < observers.size(); loopIndex++) {
                 Observer observer = (Observer)observers.get(loopIndex);
                 observer.update(operation, record);
             }
         }
         public void editRecord(String operation, String record)
         {
             this.operation = operation;
             this.record = record;
             notifyObservers();
         }
     }
     
     public static void main(String args[])
     {
         DemoObserver dm = new DemoObserver();
         DemoObserver.Database database = dm.new Database();
         DemoObserver.Archiver archiver = dm.new Archiver();
         DemoObserver.Client client = dm.new Client();
         DemoObserver.Boss boss = dm.new Boss();
         
         database.registerObserver(archiver);
         database.registerObserver(client);
         database.registerObserver(boss);
         
         database.editRecord("delete", "record 1");
     }
 }
/*
 * Template pattern
 **********************/
public class TestTemplate
{
    public abstract class RobotTemplate
     {
         public final void go ()
         {
             start();
             getParts();
             assemble();
             test();
             stop();
         }
         public void start()
         {
             System.out.println("Starting...");
         }
         public void getParts()
         {
             System.out.println("Getting parts...");
         }
         public void assemble()
         {
             System.out.println("Assembling...");
         }
         public void test()
         {
             System.out.println("Testing...");
         }
         public void stop()
         {
             System.out.println("Stopping...");
         }
     }
     public class AutomotiveRobot extends RobotTemplate
     {
         private String name;
         public AutomotiveRobot(String n)
         {
             name = n;
         }
         public String getName()
         {
             return name;
         }
         public void getParts()
         {
             System.out.println("Getting a carburetor...");
         }
         public void assemble()
         {
             System.out.println("Installing the carburetor...");
         }
         public void test()
         {
             System.out.println("Revving the engine...");
         }
     }
     public class CookieRobot extends RobotTemplate
     {
         private String name;
         public CookieRobot(String n)
         {
             name = n;
         }
         public String getName()
         {
             return name;
         }
         
         public void getParts()
         {
             System.out.println("Getting flour and sugar...");
         }
         public void assemble()
         {
             System.out.println("Baking a cookie...");
         }
         public void test()
         {
             System.out.println("Crunching a cookie...");
         }
     }
     
    public static void main(String args[])
    {
        TestTemplate dm = new TestTemplate();        
        TestTemplate.AutomotiveRobot automotiveRobot = dm.new AutomotiveRobot("Automotive Robot");
        TestTemplate.CookieRobot cookieRobot = dm.new CookieRobot("Cookie Robot");
        System.out.println(automotiveRobot.getName() + ":");
        automotiveRobot.go();
        System.out.println();
        System.out.println(cookieRobot.getName() + ":");
        cookieRobot.go();
    }
} 
 
/******************
 * Builder pattern
 ************/
import java.util.*;
import java.io.*;
public class DemoBuilder
{
    public interface RobotBuilder
    {
        public void addStart();
        public void addGetParts();
        public void addAssemble();
        public void addTest();
        public void addStop();
        public RobotBuildable getRobot();
    }
    public class CookieRobotBuilder implements RobotBuilder
    {
        CookieRobotBuildable robot;
        ArrayList<Integer> actions;
        public CookieRobotBuilder()
        {
            robot = new CookieRobotBuildable();
            actions = new ArrayList<Interger>();        
        }
        public void addStart()
        {
            actions.add(new Integer(1));
        }
        public void addGetParts()
        {
            actions.add(new Integer(2));        
        }
        public void addAssemble()
        {
            actions.add(new Integer(3));
        }
        public void addTest()
        {
            actions.add(new Integer(4));
        }
        public void addStop()
        {
            actions.add(new Integer(5));
        }
        public RobotBuildable getRobot()
        {
            robot.loadActions(actions);
            return robot;
        }
    }
    public class AutomotiveRobotBuilder implements RobotBuilder
    {
        AutomotiveRobotBuildable robot;
        ArrayList<Integer> actions;
        public AutomotiveRobotBuilder()
        {
            robot = new AutomotiveRobotBuildable();
            actions = new ArrayList<Interger>();        
        }
        public void addStart()
        {
            actions.add(new Integer(1));
        }
        public void addGetParts()
        {
            actions.add(new Integer(2));        
        }
        public void addAssemble()
        {
            actions.add(new Integer(3));
        }
        public void addTest()
        {
            actions.add(new Integer(4));
        }
        public void addStop()
        {
            actions.add(new Integer(5));
        }
        public RobotBuildable getRobot()
        {
            robot.loadActions(actions);
            return robot;
        }
    }
    public interface RobotBuildable
    {
        public void go();
    }
    public class CookieRobotBuildable implements RobotBuildable
    {
        ArrayList<Integer> actions;
        public CookieRobotBuildable() {}
        public void loadAction(ArrayList a)
        {
            actions = a;
        }
        public final void go()
        {
            Iterator itr = actions.iterator();
            while (itr.hasNext()) {
                switch ((Integer)itr.next()) {
                    case 1:
                        start();
                        break;
                    case 2:
                        getParts();
                        break;
                    case 3:
                        assemble();
                        break;
                    case 4:
                        test();
                        break;
                    case 5:
                        stop();
                        break;
                    default:
                        break;
                }
            }
        }
        public void start()
        {
            System.out.println("Starting...");
        }
        public void getParts()
        {
            System.out.println("Getting flour and sugar...");
        }
        public void assemble()
        {
            System.out.println("Baking a cookie...");
        }
        public void test()
        {
            System.out.println("Crunching a cookie...");
        }
        public void stop()
        {
            System.out.println("Stopping...");
        }
    }
    public class AutomotiveRobotBuildable implements RobotBuildable
    {
        ArrayList<Integer> actions;
        public AutomotiveRobotBuildable() {}
        public void loadAction(ArrayList a)
        {
            actions = a;
        }
        public final void go()
        {
            Iterator itr = actions.iterator();
            while (itr.hasNext()) {
                switch ((Integer)itr.next()) {
                    case 1:
                        start();
                        break;
                    case 2:
                        getParts();
                        break;
                    case 3:
                        assemble();
                        break;
                    case 4:
                        test();
                        break;
                    case 5:
                        stop();
                        break;
                    default:
                        break;
                }
            }
        }
        public void start()
        {
            System.out.println("Starting...");
        }
        public void getParts()
         {
             System.out.println("Getting a carburetor...");
         }
         public void assemble()
         {
             System.out.println("Installing the carburetor...");
         }
         public void test()
         {
             System.out.println("Revving the engine...");
         }
        public void stop()
        {
            System.out.println("Stopping...");
        }
    }
    public static void main(String args[])
    {
        DemoBuilder dm = new DemoBuilder();
        DemoBuilder.RobotBuilder builder;
        DemoBuilder.RobotBuildable robot;
        String response = "a";
        System.out.print( "Do you want a cookie robot [c] or an automotive one [a]? ");
        BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
        try {
            response = reader.readLine();
        } catch (IOException e){
            System.err.println("Error");
        }
        if (response.equals("c")) {
            builder = dm.new CookieRobotBuilder();
        } else {
            builder = dm.new AutomotiveRobotBuilder();
        }
        // Start the construction process
        builder.addStart();
        builder.addTest();
        builder.addAssemble();
        builder.addStop();
        
        robot = builder.getRobot();
        
        robot.go();
    }
}

/***
 * Iterator pattern
 ***********************/ 
import java.util.*;
public class DemoIterator
{ 
    public abstract class Corporate
    {
        public String getName()
        {
            return "";
        }
        public void add(Corporate c){}
        public Iterator iterator()
        {
            return null;
        }
        public void print()
        {
            
        }
    }
    public class VP extends Corporate
    {
        private String name;
        private String division;
        
        public VP(String n, String d)
        {
            name = n;
            division = d;
        }
        
        public String getName()
        {
            return name;
        }
        
        public void print()
        {
            System.out.println("Name: " + name + " Division: " + division);
        }
        public Iterator iterator()
        {
            return new VPIterator(this);
        }
    }
    public class VPIterator implements Iterator
    {
        private VP vp;
        
        public VPIterator(VP v)
        {
            vp = v
        }
        public VP next()
        {
            return vp;
        }
        public boolean hasNext()
        {
            return false;
        }
        public void remove()
        {
            
        }
    }
    public class Division extends Corporate
    {
        private Corporate[] corporate = new Corporate[100];
        private int number = 0;
        private String name;
        public Division(String n)
        {
            name = n;
        }
        public String getName()
        {
            return name;
        }
        public void add(Corporate c)
        {
            corporate[number++] = c;
        }
        public Iterator iterator()
        {
            return new DivisionIterator(VPs);
        }
        public void print()
        {
            Iterator iterator = iterator();
            while (iterator.hasNext()){
                Corporate c = (Corporate) iterator.next();
                c.print();
            }
        }
    }
    public class DivisionIterator implements Iterator
    {
        private Corporate[] corporate;
        private int location = 0;
        
        public DivisionIterator(Corporate[] c)
        {
            corporate = c;
        }
        public Corporate next()
        {
            return corporate[location++];
        }
        public boolean hasNext()
        {
            if(location < corporate.length && corporate[location] != null){
                return true;
            } else {
                return false;
            }
        }
        public void remove() {}
    }
    public class Corporation extends Corporate
    {
        private ArrayList<Corporate> corporate = new ArrayList<Corporate>();
        public Corporation()
        {}
        public void add (Corporate c)
        {
            corporate.add(c);
        }
        public void print()
        {
            Iterator iterator = corporate.iterator();
            while (iterator.hasNext()) {
                Corporate c = (Corporate) iterator.next();
                c.print();
            }
        }
    }
    public static void main(String args[])
    {
        DemoIterator dm = new DemoIterator();
        DemoIterator.Corporation corporation = dm.new Corporation();
        
        DemoIterator.Division rnd = dm.new Division("R&D");
        rnd.add(dm.new VP("Steve", "R&D"));
        rnd.add(dm.new VP("Mike", "R&D"));
        rnd.add(dm.new VP("Nancy", "R&D"));
        
        DemoIterator.Division sales = dm.new Division("Sales");
        sales.add(dm.new VP("Ted", "Sales"));
        sales.add(dm.new VP("Bob", "Sales"));
        sales.add(dm.new VP("Carol", "Sales"));
        
        DemoIterator.Division western = dm.new Division("Western Sales");
        western.add(dm.new VP("Wally", "Western Sales"));
        western.add(dm.new VP("Andre", "Western Sales"));
        sales.add(western);
        
        VP vp = dm.new VP("Cary", "At Large");
        corporation.add(rnd);
        corporation.add(sales);
        corporation.add(vp);
        
        corporation.print();
    }
}
/*
 * Proxy pattern
 ***/
import java.util.*;
import java.lang.Math;
public class DemoProxy{
    public interface AutomatInterface
    {
        public void gotApplication();
        public void checkApplication();
        public void rentApartment();
        public void setState(State s);
        public State getWaitingState();
        public State getGotApplication();
        public State getApartmentRentedState();
        public State getFullRentedState();
        public int getCount();
        public void setCount(int n);     
    }
    public class Automat implements AutomatInterface
    {
        State waitingState;
        State gotApplication;
        State apartmentRentedState;
        State fullRentedState;
        State state;
        int count;
        public Automat(int n)
        {
            count = n;
            waitingState = new WaitingState(this);
            gotApplication = new GotApplicationState(this);
            apartmentRentedState = new ApartmentRentedState(this);
            waitingState = new WaitingState(this);
            state = waitingState;
        }
        public void gotApplication()
        {
            System.out.println(state.gotApplication());
        }
        public void checkApplication()
        {
            System.out.println(state.checkApplication());
        }
        public void rentApartment()
        {
            System.out.println(state.rentApartment());
            System.out.println(state.dispenseKeys());
        }

        public State getGotApplication()
        {
            return gotApplication;
        }
        public State getWaitingState()
        {
            return waitingState;
        }

        public State getApartmentRentedState()
        {
            return apartmentRentedState;
        }
        public State getFullRentedState()
        {
            return fullRentedState;
        }
        public int getCount()
        {
            return count;
        }
       public void setCount(int n)
       {
           count = n;
       }
       public void setState(State s)
       {
           state = s;
       }
    }
    public interface State
    {
        public String gotApplication();
        public String checkApplication();
        public String rentApartment();
        public String dispenseKeys();
    }
    public class WaitingState implements State
    {
        AutomatInterface automat;
        public WaitingState(AutomatInterface a)
        {
            automat = a;
        }
        public String gotApplication()
        {
            automat.setState(automat.getGotApplication());
            return "Thanks for the application";
        }
        public String checkApplication()
        {
            return "You have to submit an application.";
        }

        public String rentApartment()
        {
            return "You have to submit an application."
        }

        public String dispenseKeys()
        {
            return "You have to submit an application.";
        }
    }
    public class GotApplicationState implements State {
        AutomatInterface automat;
        Random random;
        public GotApplicationState(AutomatInterface a) {
            automat = a;
            random = new Random(System.currentTimeMillis());
        }
        public String gotApplication()
        {

            return "We already got your application.";

        }
        public String checkApplication()
        {
            int yesno = random.nextInt() % 10;
            if (yesno > 4 && automat.getCount() > 0) {
                automat.setState(automat.getApartmentRentedState());
                return "Congratulations, you were approved.";
            } else {
                automat.setState(automat.getWaitingState());
                return "Sorry, you were not approved.";
            }
        }
        public String rentApartment(){
            return "You must have your application checked.";
        }
        public String dispenseKeys(){
            return "You must have your application checked.";
        }    
    }
    public class ApartmentRentedState implements State
    {
        AutomatInterface automat;
        public ApartmentRentedState(AutomatInterface a) {
            automat = a;
        }
        public String gotApplication() {
            return "Hang on, we're renting you an apartment.";
        }
        public String checkApplication() {
            return "Hang on, we're renting you an apartment.";
        }
        public String rentApartment() {
            automat.setCount(automat.getCount() - 1);
            return "Renting you an apartment...";
        }
        public String dispenseKeys() {
            if (automat.getCount() <= 0) {

                automat.setState(automat.getFullyRentedState());
            } else {
                automat.setCount(automat.getWaitingState());

            }
            return "Here are your keys!";
        }
    }
    public class FullyRentedState implements State {
        AutomatInterface automat;
        public FullyRentedState(AutomatInterface a) {
            automat = a;
        }
        public String gotApplication() {
            return "Sorry, we're fully rented.";
        }
        public String checkApplication() {
            return "Sorry, we're fully rented.";
        }

        public String rentApartment() {
            return "Sorry, we're fully rented.";
        }
        public String dispenseKeys() {
            return "Sorry, we're fully rented.";
        }

    }
    public static void main(String args[])
    {
        DemoProxy dm = new DemoProxy();
        DemoProxy.Automat automat = dm.new Automat(9);
        automat.gotApplication();
        automat.checkApplication();
        automat.rentApartment();
    }
}
/*
 * Command pattern
 **/
public class DemoCommand { 
    public interface Receiver {
        public void connect();
        public void diagnostics();
        public void reboot();
        public void shutdown();
        public void disconnect();
    }
    public class AsiaServer implements Receiver {
        public AsiaServer() {
            
        }
        public void connect() {
            System.out.println("You're connected to the Asia server.");
        }
        public void diagnostics() {
            System.out.println("The Asia server diagnostics check out OK.");
        }
        public void reboot() {
            System.out.println("Rebooting the Asia server.");
        }
        public void shutdown() {
            System.out.println("Shutting down the Asia server.");
        }
        public void disconnect() {
            System.out.println("You're disconnected from the Asia server.");
        }   
    }
    public class EuroServer implements Receiver {
        public EuroServer() {
            
        }
        public void connect() {
            System.out.println("You're connected to the Euro server.");
        }
        public void diagnostics() {
            System.out.println("The Euro server diagnostics check out OK.");
        }
        public void reboot() {
            System.out.println("Rebooting the Euro server.");
        }
        public void shutdown() {
            System.out.println("Shutting down the Euro server.");
        }
        public void disconnect() {
            System.out.println("You're disconnected from the Euro server.");
        }
    }
    public class USServer implements Receiver {
        public USServer() {
            
        }
        public void connect() {
            System.out.println("You're connected to the US server.");
        }
        public void diagnostics() {
            System.out.println("The US server diagnostics check out OK.");
        }
        public void reboot() {
            System.out.println("Rebooting the US server.");
        }
        public void shutdown() {
            System.out.println("Shutting down the US server.");
        }
        public void disconnect() {
            System.out.println("You're disconnected from the US server.");
        }
    }
    public interface Command {
        public void execute();
    } 
    public class ShutDownCommand implements Command {
        Receiver receiver;
        public ShutDownCommand(Receiver r) {
            receiver = r;
        }
        public void execute() {
            receiver.connect();
            receiver.shutdown();
            receiver.disconnect();
            System.out.println();
        }
    }
    public class RunDiagnosticsCommand implements Command {
        Receiver receiver;
        RunDiagnosticsCommand(Receiver r) {
            receiver = r;
        }
        public void execute() {
            receiver.connect();
            receiver.diagnostics();
            receiver.disconnect();
            System.out.println();
        }
    } 
    public class RebootCommand implements Command {
        Receiver receiver;
        public RebootCommand(Receiver r) {
            receiver = r;
        }
        public void execute() {
            receiver.connect();
            receiver.reboot();
            receiver.disconnect();
            System.out.println();
        }
    }
    public class Invoker {
        Command command;
        public Invoker() {}
        public void setCommand(Command c) {
            command = c;
        }
        public void run() {
            command.execute();
        }
    }
    public static void main(String args[])
    {
        DemoCommand dm = new DemoCommand();
        DemoCommand.Invoker invoker = dm.new Invoker();
        DemoCommand.AsiaServer asiaServer = dm.new AsiaServer();
        DemoCommand.EuroServer euroServer = dm.new EuroServer();        
        DemoCommand.USServer usServer = dm.new USServer();
        
        DemoCommand.ShutDownCommand shutdownAsia = dm.new ShutDownCommand(asiaServer);
        DemoCommand.RunDiagnosticsCommand diagnosticsAsia = dm.new RunDiagnosticsCommand(asiaServer);
        DemoCommand.RebootCommand rebootAsia = dm.new RebootCommand(asiaServer);
        
        invoker.setCommand(shutdownAsia);
        invoker.run();
        invoker.setCommand(diagnosticsAsia);
        invoker.run();
        invoker.setCommand(rebootAsia);
        invoker.run();
    }
}
/*
 * Mediator pattern
 **/
import java.io.*;
public class DemoMediator {
    public class Welcome {
        Mediator mediator;
        String response = "n";
        public Welcome (Mediator m) {
            mediator = m;
        }
        public void go() {
            System.out.print("Do you want to shop? [y/n]? ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                response = reader.readLine();            
            } catch (IOException e) {
                System.err.println("Error");
            }
            if (response.equals("y")) {
                mediator.handle("welcome.shop");
            } else {
                mediator.handle("welcome.exit");
            }
        }
    }
    public class Shop {
        Mediator mediator;
        String response = "n";
        public Shop(Mediator m) {
            mediator = m;
        }
        public void go() {
            System.out.print("Are you ready to purchase? [y/n]? ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                response = reader.readLine();
            } catch (IOException e) {
                System.err.println("Error");
            }
            if (response.equals("y")) {
                mediator.handle("shop.purchase");
            } else {
                mediator.handle("shop.exit");
            }
        }
    }
    public class Purchase {
        Mediator mediator;
        String response = "n";
        public Purchase(Mediator m) {
            mediator = m;
        }
        public void go() {
            System.out.print("Buy the item now? [y/n]? ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                response = reader.readLine();
            } catch (IOException e) {
                System.err.println("Error");
            }
            if (response.equals("y")) {
                System.out.println("Thanks for your purchase.");
            }
            mediator.handle("purchase.exit");
        }
    }
    public class Exit {
        Mediator mediator;
        public Exit (Mediator m) {
            mediator = m;
        }
        public void go() {
            System.out.println("Please come again sometime.");
        }
    }
    public class Mediator {
        Welcome welcome;
        Shop shop;
        Purchase purchase;
        Exit exit;
        public Mediator() {
            welcome = new Welcome(this);
            shop = new Shop(this);
            purchase = new Purchase(this);
            exit = new Exit(this);
        }
        public void handle(String state) {
            if(state.equals("welcome.shop")) {
                shop.go();
            } else if (state.equals("shop.purchase")) {
                purchase.go();
            } else if (state.equals("welcome.exit")) {
                exit.go();
            } else if (state.equals("shop.exit")) {
                exit.go();
            } else if (state.equals("purchase.exit")) {
                exit.go();
            }
        }
        public Welcome getWelcome() {
            return welcome;
        }
    }
    public static void main(String args[]) {
        DemoMediator dm = new DemoMediator();
        DemoMediator.Mediator mediator = dm.new Mediator();
        mediator.getWelcome().go();
    }
}

