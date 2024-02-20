class Solution3:
    def lengthOfLongestSubstring(self, s: str) -> int:
        occur = set()
        n = len(s)
        l, r, ans = 0, 0, 0
        while r < n:
            ch = s[r]
            while ch in occur:
                occur.remove(s[l])
                l += 1
            occur.add(ch)
            r += 1
            ans = max(ans, r - l)
        return ans


if __name__ == "__main__":
    t = Solution3()
    print(t.lengthOfLongestSubstring("dvds"))