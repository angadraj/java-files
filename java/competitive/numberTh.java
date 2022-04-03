import java.util.*;
import java.io.*;
class numberTh {
    
    // gcd and lcm of two numbers
    public static int gcdAndLcm(int a, int b) {
        int oa = a, ob = b;
        while (a % b != 0) {
            int rem = a % b;
            a = b;
            b = rem;
        }
        // gcd is b
        int lcm = (oa * ob) / b;
        return lcm;
    }

    static class pair {
        long x, y, gcd;
        public pair() {}
        public pair(long x, long y, long gcd) {
            this.x = x;
            this.y = y;
            this.gcd = gcd;
        }
    }

    // extended euclidean algo
    public static pair extendedEuclidean(long a, long b) {
        // ax + by = gcd(a, b);
        if (b == 0) {
            return new pair(1, 0, a);
        }
        pair recAns = extendedEuclidean(b, a % b);
        pair self = new pair();
        self.x = recAns.y;
        self.y = recAns.x - ((a / b) * recAns.y);
        self.gcd = recAns.gcd; 
        return self;
    }

    // linear dophantine eqn
    public static pair dophantine(long a, long b, int k) {
        // given ax + by = k * gcd(a, b);
        pair ans = extendedEuclidean(a, b);
        ans.x *= k;
        ans.y *= k;
        return ans;
    }

    // euler totient function
    public static int eulerTotient(int n) {
        // total co-primes : [1, n]
        int count = n;
        // visit all prime factors
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                // remove all the multiples of i from count
                count -= (count / i);
                while (n % i == 0) {
                    n = n / i;
                }
            }
        }
        // for example n = 26, 13 will remain because it is a prime number
        if (n > 1) {
            count -= (count / n);
            // remove all the multiples of left over prime number
        }
        return count;
    }

    // wilsons theoram
    public static long wilsons(int n, int p) {
        /// find : (n!) mod p
        if (n >= p) return 0;
        long ans = p - 1;
        for (int i = n + 1; i < p; i++) {
            ans = (ans * pow(i, p - 2, p)) % p;
        }
        return ans;
    }

    public static long pow(long x, long n, int p) {
        // (x ^ n) mod p
        long ans = 1;
        while (n > 0) {
            if ((n & 1) == 1) {
                ans = (ans * x) % p; 
                n--; // make odd power to even
            } 
            x = (x * x) % p;
            n = n / 2;
        }
        return (ans);
    }

    // ncr mod p
    public static long[][] createNcr(int n, int r, int mod) {
        long[][] dp = new long[n + 1][r + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j <= i && j < dp[0].length; j++) {
                if (i == 0 ) dp[i][j] = 0;
                else if (j == 0) dp[i][j] = 1;
                else {
                    dp[i][j] = (dp[i - 1][j] + dp[i - 1][j - 1]) % mod;
                }
            }
        }
        return dp;
    }

    public static void combiWithMod(int t) throws IOException {
        int mod = (int)(1e9) + 7;
        StringBuilder res = new StringBuilder();
        long[][] dp = createNcr(3005, 3005, mod);
        // if memory is not an issue then you can use 5005 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (t-- > 0) {
            String[] s = br.readLine().split(" ");
            int n = Integer.parseInt(s[0]);
            int r = Integer.parseInt(s[1]);
            // don't print here, we need to reduce the interaction with console
            res.append(dp[n][r] + "\n");
        }
        System.out.println(res);
    }

    // no min no max
    public static void noMinNoMax(long[] arr, int k) {
        int n = arr.length;
        customSort(arr);
        int mod = (int)(1e9) + 7;
        int mod1 = (int)(1e9) + 6; // this will be passed in power so a ^ (b % mod - 1)
        long[][] dp = createNcr(n, k, mod1);

        long total = dp[n - 1][k - 1];
        long[] count = new long[n];
        for (int i = 1; i <= n / 2; i++) {
            long minCount = 0;
            long maxCount = 0;

            if (i >= k - 1) {
                // to pick i c k - 1
                maxCount = dp[i][k - 1];
            }
            if (n - i - 1 >= k - 1) {
                minCount = dp[n - i - 1][k - 1];
            }
            long valid = (total - minCount + mod1) % mod1;
            valid = (valid - maxCount + mod1) % mod1;
            count[i] = valid;
            count[n - 1 - i] = valid;
        }
        long ans = 1;
        for (int i = 1; i < n - 1; i++) {
            long pow = pow(arr[i], count[i], mod);
            ans = (ans * pow) % mod;
        }
        System.out.println(ans);
    }

    // worst case: nlogn
    public static void customSort(long[] arr) {
        ArrayList<Long> list = new ArrayList<>();
        for (long val: arr) list.add(val);
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
    }

    // prime factors in logn
    public static void primeFactors() {
        // find all factors
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int t = sc.nextInt();
        int[] arr = new int[(int)(1e5) + 1];
        sieve(arr);
        while (t-- > 0) {
            int n = sc.nextInt();
            ArrayList<Integer> ans = factors(arr, n);
            for (int val: ans) {
                sb.append(val + " ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public static ArrayList<Integer> factors(int[] arr, int n) {
        // logn 
        ArrayList<Integer> ans = new ArrayList<>();
        while (n > 1) {
            ans.add(arr[n]);
            n = n / arr[n];
        }
        return ans;
    }

    public static void sieve(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        for (int i = 2; i * i < n; i++) {
            if (arr[i] == i) {
                for (int j = i * i; j < n; j += i) {
                    if (arr[j] == j) {
                        arr[j] = i;
                    }
                }
            }
        }
    }

    // all factors 
    static int[] spf = new int[(int)(1e5) + 1];
    public static void sieve() {
        spf[1] = 1;
        for (int i = 2; i < spf.length; i++) {
            spf[i] = i;
        }
        for (int i = 2; i * i < spf.length; i++) {
            if (spf[i] == i) {
                for (int j = i * i; j < spf.length; j += i) {
                    if (spf[j] == j) spf[j] = i;
                }
            }
        }
    }

    public static ArrayList<Integer> getFactorisation(int x) {
        ArrayList<Integer> res = new ArrayList<>();
        while (x != 1) {
            res.add(spf[x]);
            x = x / spf[x];
        }
        return res;
    }

    public static void allFactors() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sieve();
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            if (n == 1) {
                sb.append("1\n");
                continue;
            }
            ArrayList<Integer> list = getFactorisation(n);
            int ans = 1, count = 1;
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i) == list.get(i - 1)) {
                    count++; 
                } else {
                    // add the count in ans
                    ans = ans * (count + 1);
                    count = 1;
                }
            }
            // for last bucket
            ans = ans * (count + 1);
            sb.append(ans + "\n");
        }
        System.out.println(sb);
    }

    // count of common factors in a and b
    public static void commonFactorsBetween2(int a, int b) {
        sieve();
        while (a % b != 0) {
            int rem = a % b;
            a = b;
            b = rem;
        }
        if (b == 1) {
            System.out.println("1");
            return;
        }
        ArrayList<Integer> ans = getFactorisation(b);
        System.out.println(ans);
    }

    public static long powWithoutMod(long x, long n) {
        long ans = 1;
        while (n > 0) {
            if ((n & 1) == 1) {
                ans *= x;
                n--;
            }
            x *= x;
            n = n / 2;  
        }
        return ans;
    }

    // sum of factors (proper factors, factors excluding itself)
    public static void sumofFactors(int n) {
        sieve();
        if (n == 1) {
            System.out.println("0\n");    
            return;
        }
        ArrayList<Integer> list = getFactorisation(n);
        long prd = 1, count = 1;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) == list.get(i - 1)) count++;
            else {
                long pow = powWithoutMod(list.get(i - 1), count + 1);
                prd *= ((pow - 1) / (list.get(i - 1) - 1));
                count = 1;
            }
        }
        long pow = powWithoutMod(list.get(list.size() - 1), count + 1);
        prd *= ((pow - 1) / (list.get(list.size() - 1) - 1));    
        System.out.println(prd - n);
    }

    // i hate 1111: codeforces
    public static void iHate111(long x) {
        long b = x % 11;
        long val = x - 111 * b;
        if ((val >= 0) && (val % 11 == 0)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    public static void solve() throws Exception {
        sumofFactors(36);
        // System.out.println(ans);
    }

    public static void main(String args[]) throws Exception {
        solve();
    }
}