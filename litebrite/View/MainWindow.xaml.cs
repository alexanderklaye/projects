/*
 * Program Name: Project3_LiteBrite
 * Coder: Alex Laye
 * Date: April 26, 2019
 * Purpose:  this is the code behind for the litebrite project. It has the logic for the drag and drop functionality
 * 
 */


using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Shapes;

using Project3_LiteBrite.ViewModel;

namespace Project3_LiteBrite.View
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private ViewModelMain vmm;

        public MainWindow()
        {
            InitializeComponent();
            vmm = new ViewModelMain();
            this.DataContext = vmm;
        }
        
        // ancestor code from class
        private static T FindAncestor<T>(DependencyObject current)
            where T : DependencyObject
        {
            do
            {
                if (current is T)
                {
                    return (T)current;
                }
                current = VisualTreeHelper.GetParent(current);
            }
            while (current != null);
            return null;
        }

        private void Colour_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.LeftButton == MouseButtonState.Pressed)
            {
                ListBox listBox = sender as ListBox;
                ListBoxItem colorListbox = FindAncestor<ListBoxItem>((DependencyObject)e.OriginalSource);
                if (colorListbox != null)
                {
                    string col = listBox.ItemContainerGenerator.ItemFromContainer(colorListbox).ToString();
                    DataObject dataObj = new DataObject(typeof(string), col);
                    DragDrop.DoDragDrop(colorListbox, dataObj, DragDropEffects.Copy);
                }
            }
        }
      
        private void Colour_DragEnter(object sender, DragEventArgs e)
        {
            if (!e.Data.GetDataPresent(typeof(string)) || sender == e.Source)
            {
                e.Effects = DragDropEffects.Copy;
            }
        }

        private void Colour_Drop(object sender, DragEventArgs e)
        {
            if (e.Data.GetDataPresent(typeof(string)))
            {
                string col = e.Data.GetData(typeof(string)).ToString();

                Rectangle r = sender as Rectangle;
                var y = Grid.GetColumn(r);
                var x = Grid.GetRow(r);
                vmm.PopulateShapes(x, y, col);
            }
        }
      
    }
}
