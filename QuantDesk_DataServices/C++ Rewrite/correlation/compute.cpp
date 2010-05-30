
#include <fstream>
#include <iostream>
#include <math.h>

using namespace std;

inline double
correl (double *arr1, double *arr2, int len)
{
  int observations = 0, count1 = 0, count2 = 0;
  double mean1 = 0, mean2 = 0, squareSum1 = 0, squareSum2 = 0, covariance = 0;
  for (int i = 0; i < len; i++)
    {
      if (arr1[i] != 666 && arr2[i] != 666)
	{
	  observations++;
	  mean1 += arr1[i];
	  mean2 += arr2[i];
	}
      if (arr1[i] != 666)
	count1++;
      if (arr2[i] != 666)
	count2++;
    }
  if (count1 < count2)
    {
      if (count1 * 100 / count2 < 10)
	return -3;
    }
  else if (count2 * 100 / count1 < 10)
    return -3;
  if (observations > 0)
    {
      mean1 /= observations;
      mean2 /= observations;
      for (int i = 0; i < len; i++)
	{
	  if (arr1[i] != 666 && arr2[i] != 666)
	    {
	      squareSum1 += (arr1[i] - mean1) * (arr1[i] - mean1);
	      squareSum2 += (arr2[i] - mean2) * (arr2[i] - mean2);
	      covariance += (arr1[i] - mean1) * (arr2[i] - mean2);
	    }
	}
      if (squareSum1 == 0 || squareSum2 == 0)
	{
	  return -3;
	}
      covariance /= observations;

    }
  else
    return -3;
  return covariance / (sqrt (squareSum1 / observations) *
		       sqrt (squareSum2 / observations));
}

int
main ()
{
  fstream in ("data.db");
  ofstream out ("correlations.sql");
  out.precision (0);
  int n, m, tmp;
  double value;
  in >> n >> m;
  in.get ();

  double **arr = new double *[n];
  int *stock_id = new int[n];

  cout << "Reading.." << endl;
  for (int i = 0; i < n; i++)
    {
      tmp = in.peek ();
      if (tmp != '\n')
	{
	  in >> stock_id[i];

	  arr[i] = new double[m];
	  double *temp = arr[i];
	  for (int j = 0; j < m; j++)
	    in >> temp[j];
	  in.get ();
	}
      in.get ();
    }
  cout << "Start calc." << endl;
  int perc_last = -1;
  bool k = false;
  out << "TRUNCATE TABLE correlations;" << endl;

  for (int i = 0; i < n; i++)
    {
      if (arr[i])
	{
	  k = false;
	  for (int j = i + 1; j < n; j++)
	    if (arr[j])
	      {
		value = correl (arr[i], arr[j], m);
		if (value != -3)
		  {
		    if (!k)
		      out << "INSERT INTO correlations values ";
		    if (k)
		      out << ",";
		    out << "(" << stock_id[i] << "," 
			<< stock_id[j] << "," << (int)(value*127) << ")";
		    k = true;
		  }
	      }
	  out << ";" << endl;
	}
      int perc = i * 100 / n;
      if (perc != perc_last)
	{
	  cout << perc << "%" << endl;
	  perc_last = perc;
	}
    }

  in.close ();
  out.close ();
  cout << "ok" << endl;
  return 0;
}
