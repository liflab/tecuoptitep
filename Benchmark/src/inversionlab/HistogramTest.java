package inversionlab;

import ca.uqac.lif.spreadsheet.Spreadsheet;
import ca.uqac.lif.spreadsheet.chart.Chart;
import ca.uqac.lif.spreadsheet.chart.gnuplot.Gnuplot;
import ca.uqac.lif.spreadsheet.chart.gnuplot.GnuplotHistogram;

public class HistogramTest
{

  public static void main(String[] args)
  {
    /*
    Spreadsheet s = new Spreadsheet(3, 4);
    s.set(0, 0, "Random").set(1, 0, 3).set(2, 0, 1);
    s.set(0, 0, "Random").set(1, 0, 4).set(2, 0, 2);
    s.set(0, 0, "Random").set(1, 0, 5).set(2, 0, 3);
    
    s.set(0, 0, "Inversion").set(1, 0, 3).set(2, 0, 2);
    s.set(0, 0, "Inversion").set(1, 0, 4).set(2, 0, 1);
    s.set(0, 0, "Inversion").set(1, 0, 5).set(2, 0, 3);
    */
    Spreadsheet s = new Spreadsheet(3, 4);
    s.set(0, 0, "Length").set(1, 0, "Random").set(2, 0, "Inversion");
    s.set(0, 1, 3).set(1, 1, 1).set(2, 1, 2);
    s.set(0, 2, 4).set(1, 2, 2).set(2, 2, 1);
    s.set(0, 3, 5).set(1, 3, 3).set(2, 3, 3);
    
    GnuplotHistogram h = new GnuplotHistogram();
    h.toGnuplot(System.out, s, Gnuplot.GP, false);

  }

}
