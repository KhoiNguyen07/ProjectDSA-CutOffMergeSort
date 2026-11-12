import matplotlib.pyplot as plt
import pandas as pd
df = pd.read_csv("benchmark_results.csv")
summary = (
    df.groupby(["ArraySize", "Cutoff"])["TimeMs"]
    .agg(["mean", "std"])
    .reset_index()
)
print("--- BẢNG KẾT QUẢ TRUNG BÌNH (ms) ---")
pivot_table = summary.pivot(index="Cutoff", columns="ArraySize", values="mean")
print(pivot_table)
plt.figure(figsize=(10, 6))
for size in df["ArraySize"].unique():
    subset = summary[summary["ArraySize"] == size]
    plt.plot(
        subset["Cutoff"],
        subset["mean"],
        marker="o",
        label=f"N = {size:,} elements",
    )
plt.title(
    "Ảnh hưởng của CUTOFF đến thời gian thực thi của Mergesort",
    fontsize=14,
    fontweight="bold",
)
plt.xlabel("Giá trị CUTOFF", fontsize=12)
plt.ylabel("Thời gian chạy trung bình (ms)", fontsize=12)
plt.grid(True, linestyle="--", alpha=0.6)
plt.axvline(
    x=7,
    color="gray",
    linestyle=":",
    alpha=0.7,
    label="Mốc giáo trình cũ (CUTOFF = 7)",
)
plt.axvline(
    x=10,
    color="orange",
    linestyle=":",
    alpha=0.7,
    label="Mốc giáo trình mới (CUTOFF = 10)",
)
plt.legend()
plt.tight_layout()
plt.savefig("cutoff_benchmark_plot.png", dpi=300)
plt.show()