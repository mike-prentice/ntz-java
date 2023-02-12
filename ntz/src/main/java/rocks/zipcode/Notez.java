package rocks.zipcode;

import java.util.Scanner;

/**
 * ntz main command.
 */
public final class Notez {

    private FileMap filemap = new FileMap();

    public Notez() {
        this.filemap = new FileMap();
    }

    /**
     * Says hello to the world.
     *
     * @param args The arguments of the program.
     */
    public static void main(String argv[]) {
        boolean _debug = true;
        // for help in handling the command line flags and data!
        if (_debug) {
            System.err.print("Argv: [");
            for (String a : argv) {
                System.err.print(a + " ");
            }
            System.err.println("]");
        }

        Notez ntzEngine = new Notez();

        ntzEngine.loadDatabase();

        /*
         * You will spend a lot of time right here.
         *
         * instead of loadDemoEntries, you will implement a series
         * of method calls that manipulate the Notez engine.
         * See the first one:
         */
        //ntzEngine.loadDemoEntries();

        ntzEngine.saveDatabase();

        if (argv.length == 0) { // there are no command line arguments
            // just print the contents of the file map.
            ntzEngine.printResults();
        } else {
            if (argv[0].equals("-r")) {
                // Scanner scanner = new Scanner(System.in); // Create a Scanner object
                // System.out.println("Enter a catagory for your note");
                // String catagory = scanner.nextLine(); // Read user input
                // System.out.println("Enter Note text... ");
                // String note = scanner.nextLine();

                ntzEngine.addToCategory("General", argv[1]);
                //scanner.close();
            } else if (argv[0].equals("-c")) {
                ntzEngine.addToCategory(argv[1], argv[2]);
            } else if (argv[0].equals("-f")) {
                ntzEngine.removeNote(argv[1]);
            } else if (argv[0].equals("-fa")) {
                ntzEngine.deleteAllNotes();
            }


            else if (argv[0].equals("ntz")) {
                ntzEngine.printResults();
            }
            // this should give you an idea about how to TEST the Notez engine
            // without having to spend lots of time messing with command line arguments.
        }

        /*
         * what other method calls do you need here to implement the other commands??
         */

    }

    private void deleteAllNotes() {
        filemap.clear();
        saveDatabase();
    }

    private void removeNote(String string) {
        filemap.remove(string);
    }

    private void addToCategory(String category, String note) {
    	if(filemap.containsKey(category)) {
    		filemap.get(category).add(note);
    		saveDatabase();
    	}else {
    		filemap.put(category, new NoteList(note));
            saveDatabase();
    	}
    }

    private void saveDatabase() {
        filemap.save();
    }

    private void loadDatabase() {
        filemap.load();
    }

    public void printResults() {
        System.out.println(this.filemap.toString());
    }

    public void loadDemoEntries() {
        filemap.put("General", new NoteList("The Very first Note"));
        filemap.put("note2", new NoteList("A secret second note"));
        filemap.put("category3", new NoteList("Did you buy bread AND eggs?"));
        filemap.put("anotherNote", new NoteList("Hello from ZipCode!"));
    }
    /*
     * Put all your additional methods that implement commands like forget here...
     */

}
