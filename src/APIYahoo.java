/**
 * Created by glenn on 26/08/2017.
 */

import com.sun.istack.internal.NotNull;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public final class APIYahoo extends MyPanel {
  JTable table = null;


  APIYahoo() throws IOException {
    super();

    String titre[] = {"Date", "Open", "High", "Low", "Close", "Adj Close"};
    final DefaultTableModel tableModel = new DefaultTableModel();
    tableModel.setColumnIdentifiers(titre);
    table = new JTable(tableModel);

    JScrollPane menuDeroulant = new JScrollPane(table);

    setLayout(new BorderLayout());
    this.add(menuDeroulant, BorderLayout.CENTER);

    updateCotations("TSLA");
  }


  final void updateCotations(final String ticker) {
    DefaultTableModel locModel = (DefaultTableModel) table.getModel();
    while (locModel.getRowCount() > 0) {
      locModel.removeRow(0);
    }
    List<HistoricalQuote> historics = null;
    try {
      Stock tesla = YahooFinance.get(ticker, true);
      historics = tesla.getHistory();
    } catch (IOException ioe) {
      System.out.println("Probleme d'entree sortie sur les fichiers de cotation yahoo finance");
    }
    if (historics == null) {
      return;
    }
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    historics.forEach(elementHistoric ->
                              locModel.addRow(new Object[] {
                                      dateFormat.format(elementHistoric.getDate().getTime()),
                                      elementHistoric.getOpen(),
                                      elementHistoric.getHigh(),
                                      elementHistoric.getLow(),
                                      elementHistoric.getClose(),
                                      elementHistoric.getAdjClose()}
                              )
    );

  }

  public String getTitre() {return "Cotation";
  }





}