/*
 * Program Name: Project3_LiteBrite
 * Coder: Alex Laye
 * Date: April 26, 2019
 * Purpose:  ViewModelMain for the project; it has the binding information inside of it
 * 
 */
using System;
using System.Collections.ObjectModel;
using Microsoft.Win32;
using System.IO;
using System.Windows;

using Project3_LiteBrite.Helpers;
using Project3_LiteBrite.Model;
using Project3_LiteBrite.View;

namespace Project3_LiteBrite.ViewModel
{
    class ViewModelMain : ViewModelBase
    {
        private string saveFile_;
        private Random random;
        const int cellCounter = 50; // note: this will mean it's 50x50

        public ObservableCollection<string> colorChoice { get; set; }
        public ObservableCollection<LiteBriteColours> allShapes { get; set; }

        public RelayCommand commandOpen { get; set; }
        public RelayCommand commandSave { get; set; }
        public RelayCommand commandExit { get; set; }
        public RelayCommand commandNewBoard { get; set; }
        public RelayCommand commandRandomize { get; set; }
        public RelayCommand commandAbout { get; set; }

        public ViewModelMain()
        {
            random = new Random();
            colorChoice = new ObservableCollection<string>
            {
                "Black" ,
                "Red" ,
                "Green" ,
                "Blue" ,
                "Yellow" ,
                "Purple" ,
                "Brown" ,
                "Teal" ,
                "White" ,
                "MediumVioletRed" ,
                "GreenYellow" ,
                "Cyan" ,
                "Goldenrod" ,
                "Fuchsia" ,
                "RosyBrown" ,
                "Turquoise" ,
                "Transparent"
            };
            allShapes = new ObservableCollection<LiteBriteColours>();

            for (int i = 0; i < cellCounter * cellCounter; i++)
                allShapes.Add(new LiteBriteColours("Transparent", i / 50, i % 50));

            //Relay Commands
            commandOpen = new RelayCommand(OpenFile);
            commandSave = new RelayCommand(SaveFile);
            commandExit = new RelayCommand(ExitProgram);
            commandNewBoard = new RelayCommand(NewGame);
            commandRandomize = new RelayCommand(RandomBoard);
            commandAbout = new RelayCommand(OpenAbout);
        }
 
        private void OpenFile(object obj)
        {
            OpenDocument();
        }

        public void OpenDocument()
        {
            OpenFileDialog ofd = new OpenFileDialog
            {
                Filter = "Plain Text File (*.txt)|*.txt", InitialDirectory = Directory.GetCurrentDirectory()
            };

            if (ofd.ShowDialog() == true)
            {
                saveFile_ = ofd.FileName;
                ReadFileName();
            }
        }

        private void ReadFileName()
        {
            using (var reader = new StreamReader(saveFile_))
            {
                int rowCounter = 0;
                while (!reader.EndOfStream)
                {
                    var line = reader.ReadLine();
                    var values = line.Split('|');

                    for (int i = 0; i < values.Length; ++i)
                    {
                        if (values[i] != "")
                        {
                            allShapes[rowCounter * 50 + i].Colour = values[i];
                        }
                    }
                    ++rowCounter;
                }
            }
        }
        
        private void SaveFile(object obj)
        {
            GetSaveLocation();
        }

        public void GetSaveLocation()
        {
            SaveFileDialog sfd = new Microsoft.Win32.SaveFileDialog
            {
                FileName = "LiteBriteFile", DefaultExt = ".txt", Filter = "Plain Text File (*.txt)|*.txt", InitialDirectory = Directory.GetCurrentDirectory()
            };

            bool? result = sfd.ShowDialog();

            if (result == true)
            {
                saveFile_ = sfd.FileName;
                SaveDocument();
            }
        }

        public void SaveDocument()
        {
            try
            {
                using (var writer_ = new StreamWriter(saveFile_))
                {
                    string temp = "";

                    for (int i = 0; i < allShapes.Count; i++)
                    {
                        if (i != 0 && i % cellCounter == 0)
                        {
                            writer_.WriteLine(temp);
                            writer_.Flush();
                            temp = "";
                        }
                        temp += allShapes[i].Colour + "|";
                    }
                    writer_.WriteLine(temp);
                    writer_.Flush();
                }
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
       
        private void ExitProgram(object obj)
        {
            Application.Current.Shutdown();
        }
        
        private void NewGame(object obj)
        {
            for (int i = 0; i < cellCounter * cellCounter; i++)
                allShapes[i].Colour = "Transparent";
        }
        
        private void RandomBoard(object obj)
        {
            for (int i = 0; i < cellCounter * cellCounter; i++)
                allShapes[i].Colour = colorChoice[random.Next(0,14)];
        }
       
        private void OpenAbout(object obj)
        {
            LiteBriteAbout aboutWindow = new LiteBriteAbout();
            aboutWindow.ShowDialog();
        }
      
        internal void PopulateShapes(int x, int y, string theItem)
        {
            foreach (var tile in allShapes)
                if (tile.Row == x && tile.Column == y)
                {
                    tile.Colour = theItem;
                    break;
                }
        }
    }
}
