package be.akimts.test.interfaces;

import be.akimts.test.models.Product;

import java.io.File;
import java.util.*;

public class MainMenu {

    private final Set<Product> products = new HashSet<>();
    private final Map<String,String> traductions;
    private final File saveFile;
    private final Scanner scanner = new Scanner(System.in);
    private long nextId;


    public MainMenu(Map<String, String> traductions, File saveFile) {
        if( traductions == null )
            throw new IllegalArgumentException();
        if( saveFile == null )
            throw new IllegalArgumentException();
        this.traductions = traductions;
        this.saveFile = saveFile;
        init();
    }

    private void init(){
        this.nextId = 1;
    }


    public void start(){
        String command;
        do {
            printMenu();
            command = askForCommand(new String[]{"1","2","3","4","q"});
            handleCommand(command);
        } while(!command.equals("q"));
    }

    private void printMenu(){
        System.out.format(
                    """
                    -------------------------
                    |=> %s
                    -------------------------
                    1 - %s
                    2 - %s
                    3 - %s
                    4 - %s
                    q - %s
                    -------------------------
                    
                    """,
                    traductions.get("menu-title"),
                    traductions.get("read-all"),
                    traductions.get("add"),
                    traductions.get("remove"),
                    traductions.get("save"),
                    traductions.get("quit")
                );
    }
    private String askForCommand(String[] possibleCommands){
        return askForCommand("Please issue a command", possibleCommands);
    }

    private String askForCommand(String message, String ...possibleCommands){
        System.out.println(message);
        String command;
        boolean included;
        do {
            System.out.print("> ");
            command = scanner.nextLine().trim();

            included = possibleCommands.length == 0;
            for (String possibleCommand : possibleCommands) {
                if(Objects.equals(possibleCommand, command)){
                    included = true;
                    break;
                }
            }
            if( !included )
                System.out.println("Invalid input, retry (possible commands: "+Arrays.toString(possibleCommands)+") :");
        } while( !included );
        return command;
    }


    private <T> T askForValue(Class<T> type, String message){
        System.out.print(message);
        do {
            try {
                String valueString = scanner.nextLine().trim();
                if (type == Long.class) {
                    return (T) Long.valueOf(Long.parseLong(valueString));
                } else if (type == String.class) {
                    return (T) valueString;
                } else if (type == Double.class) {
                    return (T) Double.valueOf( Double.parseDouble(valueString) );
                }
            }
            catch (Exception ex){
                System.out.println("Invalid value type");
            }
        } while (true);
    }
    private void handleCommand(String command){
        switch (command) {
            case "1" -> handleDisplay();
            case "2" -> handleAdd();
            case "3" -> handleRemove();
            case "4" -> handleSave();
            case "q" -> handleQuit();
        }
    }
    
    private void handleDisplay(){
        products.forEach(System.out::println);
    }
    private void handleAdd(){
        long id = this.nextId++;
        String model = askForValue(String.class, "> model(string): ");
        String brand = askForValue(String.class, "> brand(string): ");
        double price = askForValue(Double.class, "> price(number): ");
        Product toAdd =  new Product(id,model,brand,price);
        this.products.add( toAdd );
        System.out.println("Added: " + toAdd);
    }
    private void handleRemove(){
        String[] possibleIds = products.stream()
                .map(p -> p.id().toString())
                .toArray(String[]::new);
        String idString = askForCommand("Please choose an id:",possibleIds);
        long id = Long.parseLong(idString);
        products.remove(
                products.stream()
                        .filter(p -> p.id() == id)
                        .findFirst()
                        .orElseThrow()
        );
    }
    private void handleSave(){
        System.out.println("not implemented");
    }
    private void handleQuit(){
        System.out.println("Bye!");
    }
}
