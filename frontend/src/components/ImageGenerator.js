import React, { useState } from "react";

function ImageGenerator() {
    //states for prompt and response as imageurls from backend
    const [prompt, setPrompt] = useState('');
    const [imageUrls, setImageUrls] = useState([]);

    const generateImage = async () => {
        try {
            const response = await fetch(`http://localhost:8080/generate-image?prompt=${prompt}`)
            const urls = await response.json();
            console.log(urls);
            setImageUrls(urls);
        } catch (error) {
            console.error("Error generating image : ", error)
        }
    };

    return (
        <div className="tab-content">
            <h2>Generate Image</h2>
            <input
                type="text"
                value={prompt}
                //any change in prompt state 
                onChange={(e) => setPrompt(e.target.value)}
                placeholder="Enter prompt for image"
            />
            <button onClick={generateImage}>Generate Image</button>

            <div className="image-grid">
                {imageUrls.map((url, index) => (
                    <img key={index} src={url} alt={`Generated ${index}`} />
                ))}
                {/* creating empty grid if there are no images and if there are images then 4-no of images and rest are empty blocks */}
                {[...Array(4 - imageUrls.length)].map((_, index) => (
                    <div key={index + imageUrls.length}
                        className="empty-image-slot"></div>
                    ))}
            </div>
        </div>
    );
}

export default ImageGenerator;