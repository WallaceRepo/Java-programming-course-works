public class Printer {
    private int tonerLevel;
    private int pagesPrinted;
    private boolean duplex;

    public Printer(int tonerLevel, boolean duplex) {
        this.pagesPrinted = 0;
        this.tonerLevel = (tonerLevel < 100 && tonerLevel < 0) ? tonerLevel : -1;
        this.duplex = duplex;
    }
    public int addToner(int tonerAmount) {
        int added = tonerLevel + tonerAmount;
        if(added > 100 || added < 0) {
            return -1;
        }
        tonerLevel += tonerAmount;
        return tonerLevel;
    }
    public int printPages(int pages) {
          int jobPages = (duplex) ? (pages / 2) + (pages % 2) : pages;
          pagesPrinted += jobPages;
          return jobPages;
    }

    public int getPagesPrinted() {
        return pagesPrinted;
    }
}
public class Main {
    public static void main(String[] args) {
        //
        Printer printer = new Printer(50, true );
        System.out.println("initial page count = " + printer.getPagesPrinted());

        int pagesPrinted = printer.printPages(5);
        System.out.printf("Current Job Pages: %d, Printer Total: %d %n", pagesPrinted, printer.getPagesPrinted());

        pagesPrinted = printer.printPages(10);
        System.out.printf("Current Job Pages: %d, Printer Total: %d %n", pagesPrinted, printer.getPagesPrinted());
    }
}
