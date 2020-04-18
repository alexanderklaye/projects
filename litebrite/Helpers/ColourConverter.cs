/*
 * Program Name: Project3_LiteBrite
 * Coder: Lianne Wong
 * Date: April 26, 2019
 * Purpose:  converter provided by Lianne via the draganddrop in class program
 * 
 */
 using System;
using System.Globalization;
using System.Windows.Data;

namespace Project3_LiteBrite.Helpers
{
    class ColourConverter : IValueConverter
    {
        public object Convert(object value, Type targetType, object parameter, CultureInfo culture)
        {
            return value.ToString();
        }

        public object ConvertBack(object value, Type targetType, object parameter, CultureInfo culture)
        {
            throw new NotImplementedException();
        }
    }
}
