package service.statictic.templates;

public class FinanceYearReportTemplateBuilder implements ReportTemplateBuilder {

    private String header = "Raportu zysków w roku";

    private String body = "";

    private String foot = DEFAULT_FOOT;

    @Override
    public void createHeader(Object... args) {
        if (isSameMonth(args)) {
            Integer year = (Integer) args[0];
            header = String.format("Raportu zysków w roku %d\n", year);
        } else {
            header = String.format("Raportu zysków w latach od %d  do %d\n", args);
        }
    }

    @Override
    public void appendBodyBlock(Object... args) {
        body += String.format("\tRok %d (%d).\n"
                + "\tzysk z rezerwacji %.2fzł\n"
                + "\tzysk z uslug: %.2fzł\n"
                + "\tzysk z kantoru: %.2fzł\n"
                + "\tzysk sumaryczny: %.2fzł\n", args);
    }

    @Override
    public void createFoot(Object... args) {
        if (isShownLegend(args)) {
            foot = "Legenda \n"
                    + " Slupek pierwsz przedstawia zysk z rezerwacji\n"
                    + " Slupek drugi przedstawia zysk z uslug\n"
                    + " Slupek trzeci przedstawia zysk z kantoru\n"
                    + " Slupek czwarty przedstawia zysk sumaryczny\n";
        } else {
            foot = DEFAULT_FOOT;
        }
    }

    @Override
    public String build() {
        String result = header + body + foot;
        body = "";
        return result;
    }

    private boolean isSameMonth(Object... args) {
        return args != null && args.length > 0 && args[0] == args[1];
    }

    private boolean isShownLegend(Object... args) {
        return args != null && args.length > 0 && ((Integer) args[0]) > 0;
    }
}
