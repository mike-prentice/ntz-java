package rocks.zipcode;

import java.util.List;

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

        ntzEngine.saveDatabase();

        if (argv.length == 0) { // there are no command line arguments
            // just print the contents of the file map.
            ntzEngine.printResults();
        } else {
            if (argv[0].equals("-r")) {
                ntzEngine.addToCategory("General", argv[1]);
            } else if (argv[0].equals("-c")) {
                ntzEngine.addToCategory(argv[1], argv[2]);
            } else if (argv[0].equals("-f")) {
                ntzEngine.removeNote(argv[1], argv[2]);
            } else if (argv[0].equals("-fa")) {
                ntzEngine.deleteAllNotes();
            }else if (argv[0].equals("-e")) {
            	ntzEngine.editNote(argv[1], argv[2], argv[3]);
            }else if (argv[0].equals("ntz")) {
                ntzEngine.printResults();
            } else if(argv[0].equals("-d")) {
            	ntzEngine.displayCategory(argv[1]);
            }
        }
    }

    private void deleteAllNotes() {
        filemap.clear();
        saveDatabase();
    }

    private void removeNote(String category, String note) {
        filemap.get(category).remove(Integer.parseInt(note) - 1);
        saveDatabase();
    }
    
    private void editNote(String category, String note, String newNote) {
        filemap.get(category).set(Integer.parseInt(note), newNote);
        saveDatabase();
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
    
    private void displayCategory(String category) {
    	int index = 1;
    	List<String> categoryNotes = filemap.get(category);
    	for(String s : categoryNotes) {
    		System.out.println(index + ": " + s.toString());
    		index++;
    	}
    }


}
