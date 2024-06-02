import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class PollData {
    String source;
    String originalNdaSeats;
    String originalIndiaSeats;
    String originalOthSeats;
    int ndaSeats;
    int indiaSeats;
    int othSeats;

    public PollData(String source, String originalNdaSeats, String originalIndiaSeats, String originalOthSeats, int ndaSeats, int indiaSeats, int othSeats) {
        this.source = source;
        this.originalNdaSeats = originalNdaSeats;
        this.originalIndiaSeats = originalIndiaSeats;
        this.originalOthSeats = originalOthSeats;
        this.ndaSeats = ndaSeats;
        this.indiaSeats = indiaSeats;
        this.othSeats = othSeats;
    }

    public String getSource() {
        return source;
    }

    public String getOriginalNdaSeats() {
        return originalNdaSeats;
    }

    public String getOriginalIndiaSeats() {
        return originalIndiaSeats;
    }

    public String getOriginalOthSeats() {
        return originalOthSeats;
    }

    public int getNdaSeats() {
        return ndaSeats;
    }

    public int getIndiaSeats() {
        return indiaSeats;
    }

    public int getOthSeats() {
        return othSeats;
    }

    private static int parseSeats(String seatStr) {
        if (seatStr.contains("-")) {
            String[] range = seatStr.split("-");
            int low = Integer.parseInt(range[0].trim());
            int high = Integer.parseInt(range[1].trim());
            return (low + high) / 2;
        } else {
            return Integer.parseInt(seatStr.trim());
        }
    }

    public static PollData fromElements(Elements columns) {
        String sourceName = columns.get(0).text();
        String originalNdaSeats = columns.get(1).text();
        String originalIndiaSeats = columns.get(2).text();
        String originalOthSeats = columns.get(3).text();

        int ndaSeats = parseSeats(originalNdaSeats);
        int indiaSeats = parseSeats(originalIndiaSeats);
        int othSeats = parseSeats(originalOthSeats);

        return new PollData(sourceName, originalNdaSeats, originalIndiaSeats, originalOthSeats, ndaSeats, indiaSeats, othSeats);
    }
}

public class ExitPollScraper {

    public static List<PollData> scrapeExitPollData() {
        String url = "https://y20india.in/exit-poll-results-2024-live-updates/";
        List<PollData> pollDataList = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();
            Element table = doc.select("table").first();
            Elements rows = table.select("tbody tr");

            for (Element row : rows) {
                Elements columns = row.select("td");

                if (columns.size() == 4) {
                    PollData pollData = PollData.fromElements(columns);
                    pollDataList.add(pollData);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return pollDataList;
    }

    public static void main(String[] args) {
        List<PollData> data = scrapeExitPollData();
        for (PollData pd : data) {
            System.out.println("Source: " + pd.getSource() +
                    ", NDA: " + pd.getOriginalNdaSeats() +
                    " (avg: " + pd.getNdaSeats() + ")" +
                    ", INDIA: " + pd.getOriginalIndiaSeats() +
                    " (avg: " + pd.getIndiaSeats() + ")" +
                    ", OTH: " + pd.getOriginalOthSeats() +
                    " (avg: " + pd.getOthSeats() + ")");
        }
    }
}
