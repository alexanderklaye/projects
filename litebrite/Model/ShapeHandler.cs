/*
 * Program Name: Project3_LiteBrite
 * Coder: Alex Laye
 * Date: April 26, 2019
 * Purpose:  This class contains the info for the litebrite items, including row information, colour, and column information
 * 
 */

using System.ComponentModel;

namespace Project3_LiteBrite.Model
{
    class LiteBriteColours : INotifyPropertyChanged
    {
        private string colour_;

        public string Colour
        {
            get { return colour_; }
            set
            {
                if (colour_ != value)
                {
                    colour_ = value;
                    RaisePropertyChanged("Colour");
                }
            }
        }

        private int row_;

        public int Row
        {
            get { return row_; }
            set
            {
                row_ = value;
                RaisePropertyChanged("Row");
            }
        }

        private int column_;

        public int Column
        {
            get { return column_; }
            set
            {
                column_ = value;
                RaisePropertyChanged("Column");
            }
        }

        public LiteBriteColours(string colour, int row, int col)
        {
            Colour = colour;
            Row = row;
            Column = col;
        }

        public override string ToString()
        {
            return colour_;
        }

        private void RaisePropertyChanged(string prop)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(prop));
        }
        public event PropertyChangedEventHandler PropertyChanged;
    }
}
