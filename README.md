# Exit Poll Results 2024 Visualization

This project is a Java-based application that scrapes exit poll data for the 2024 elections from the [Y20 India](https://y20india.in/exit-poll-results-2024-live-updates/) website and visualizes the data using pie charts. The application allows users to select different sources of exit poll predictions and displays the results in an interactive GUI.

## Key Features
- **Web Scraping:** Uses JSoup to scrape exit poll data from a specified website.
- **Data Parsing:** Handles parsing of seat numbers, including averaging seat ranges when specified.
- **Interactive GUI:** Allows users to select different sources of exit poll predictions via a dropdown menu.
- **Data Visualization:** Utilizes JFreeChart to generate pie charts with customized colors for different political alliances.
- 
## Getting Started

### Dependencies
- [JSoup](https://jsoup.org/)
- [JFreeChart](https://sourceforge.net/projects/jfreechart/)

### Running the Application

1. **Compile and Run the Scraper:**
   - Compile and run the `ExitPollScraper` class to scrape the data.

2. **Compile and Run the GUI:**
   - Compile and run the `ExitPollGUI` class to launch the GUI and visualize the data.

### Usage

- Select a source from the dropdown menu to view the corresponding exit poll results.
- View the pie chart and the original data, including averages where applicable.

## Example Output
![image](https://github.com/ritam03/exitpollscrape/assets/121516345/2fc736b3-018f-42fc-bf48-6b71b12058a8)

## Future Improvements
- Add more detailed visualizations and analytics.
